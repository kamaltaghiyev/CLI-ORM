
import Annotations.MyColumn;
import Annotations.MyEntity;
import Annotations.MyId;
import exeption.OrmMetadataException;
import meta.BeanFieldInfo;
import meta.Pair;
import meta.TableMetaInfo;

import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrmMetaDataManagerImpl implements OrmMetaDataManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmMetaDataManagerImpl.class);
    private final OrmManager ormManager;
    private Map<Class, TableMetaInfo> tableMetaInfoCache = new ConcurrentHashMap<>();

    public OrmMetaDataManagerImpl(OrmManager ormManager) {
        this.ormManager = ormManager;
    }

    @Override
    public <T> T initProxyObject(Class<T> objectClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(objectClass);
        LOGGER.debug("init proxy object {}", objectClass.getSimpleName());
        return (T) enhancer.create();

    }


    private List<BeanFieldInfo> getFieldsInfo(Class<?> objectClass) {
        List<BeanFieldInfo> beanFieldInfoList = new ArrayList<>();
        try {
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(objectClass).getPropertyDescriptors()) {
                if (propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null) {
                    Field field = objectClass.getDeclaredField(propertyDescriptor.getName());
                    beanFieldInfoList.add(
                            new BeanFieldInfo(
                                    field,
                                    propertyDescriptor.getReadMethod(),
                                    propertyDescriptor.getWriteMethod())
                    );
                }
            }
        } catch (NoSuchFieldException | IntrospectionException e) {
            throw new OrmMetadataException(e);
        }
        return beanFieldInfoList;
    }

    @Override
    public TableMetaInfo getTableMetaInfo(Class<?> objectClass) {
        if (Enhancer.isEnhanced(objectClass)) {
            objectClass = objectClass.getSuperclass();
        }
        if (tableMetaInfoCache.containsKey(objectClass)) {
            return tableMetaInfoCache.get(objectClass);
        }

        if (objectClass.getAnnotation(MyEntity.class) == null) {
            throw new OrmMetadataException("Table annotation not found");
        }

        TableMetaInfo tableMetaInfo = new TableMetaInfo();
        tableMetaInfo.setTableName(objectClass.getAnnotation(MyEntity.class).name());
        tableMetaInfo.setBaseRows(new HashMap<>());

        List<BeanFieldInfo> beanFieldInfoList = getFieldsInfo(objectClass);

        for (BeanFieldInfo beanFieldInfo : beanFieldInfoList) {
            MyColumn column = beanFieldInfo.getField().getAnnotation(MyColumn.class);
            if (column != null) {

                    tableMetaInfo.setIdRow(Pair.of(column.name(), beanFieldInfo));

            }
        }
        for (BeanFieldInfo beanFieldInfo : beanFieldInfoList) {
            MyId name = beanFieldInfo.getField().getAnnotation(MyId.class);
            if(name!=null){
                tableMetaInfo.getBaseRows().put(MyId.name(), beanFieldInfo);
            }
        }

        if (beanFieldInfoList.size() > 0) {
            tableMetaInfoCache.put(objectClass, tableMetaInfo);
            return tableMetaInfo;
        } else {
            throw new OrmMetadataException("not mapped fields found");
        }
    }

    private List<String> getColumnsList(ResultSet rs) {
        List<String> columnsList = new ArrayList<>();
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columns = rsMetaData.getColumnCount();
            for (int x = 1; x <= columns; x++) {
                columnsList.add(rsMetaData.getColumnName(x).toLowerCase());
            }
        } catch (SQLException e) {
            throw new OrmMetadataException(e);
        }
        return columnsList;
    }


    @Override
    public <T> T fillResultSetToObject(ResultSet resultSet, T object) {
        Class<?> objectClass = Enhancer.isEnhanced(object.getClass())
                ? object.getClass().getSuperclass()
                : object.getClass();

        List<String> columnsList = getColumnsList(resultSet);
        TableMetaInfo tableMetaInfo = getTableMetaInfo(objectClass);
        try {
            Pair<String, BeanFieldInfo> idRow = tableMetaInfo.getIdRow();
            if (columnsList.contains(idRow.getFirst().toLowerCase())) {
                idRow.getSecond().getSetter().invoke(object, resultSet.getObject(idRow.getFirst().toLowerCase()));
            } else if (columnsList.size() == 1 && columnsList.contains("scope_identity()")) {
                idRow.getSecond().getSetter().invoke(object, resultSet.getObject("scope_identity()"));
                return object;
            }
            for (String baseRow : tableMetaInfo.getBaseRows().keySet()) {
                BeanFieldInfo beanFieldInfo = tableMetaInfo.getBaseRows().get(baseRow);
                if (columnsList.contains(baseRow.toLowerCase())) {
                    beanFieldInfo.getSetter().invoke(object, resultSet.getObject(baseRow.toLowerCase()));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
            throw new OrmMetadataException(e);
        }

        return object;
    }

    @Override
    public <T> T resultSetToObject(ResultSet resultSet, Class<T> objectClass) {
        T object = initProxyObject(objectClass);
        return fillResultSetToObject(resultSet, object);
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, List<Object> values) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            int parameterIndex = i + 1;
            if (value instanceof Date) {
                preparedStatement.setDate(parameterIndex, new java.sql.Date((((Date) value).getTime())));
            } else {
                preparedStatement.setObject(parameterIndex, value);
            }
        }
    }

    @Override
    public Object getFieldValue(BeanFieldInfo beanFieldInfo, Object object) {
        try {
            return beanFieldInfo.getGetter().invoke(object);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new OrmMetadataException();
        }
    }
}
