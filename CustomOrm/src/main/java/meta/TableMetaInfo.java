package meta;

import java.util.Map;

public class TableMetaInfo {

    private String tableName;
    private Pair<String, BeanFieldInfo> idRow;
    private Map<String, BeanFieldInfo> baseRows;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Pair<String, BeanFieldInfo> getIdRow() {
        return idRow;
    }

    public void setIdRow(Pair<String, BeanFieldInfo> idRow) {
        this.idRow = idRow;
    }

    public Map<String, BeanFieldInfo> getBaseRows() {
        return baseRows;
    }

    public void setBaseRows(Map<String, BeanFieldInfo> baseRows) {
        this.baseRows = baseRows;
    }


}
