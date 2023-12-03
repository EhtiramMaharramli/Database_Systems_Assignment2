import java.sql.*;

public class MetaData {

    private Connection connection;

    public MetaData(Connection connection) {
        this.connection = connection;
    }

    public void displayTableInfo() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("Tables in the Database:");
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                System.out.println("Columns:");
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String dataType = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    System.out.println("  " + columnName + " (" + dataType + ", Size: " + columnSize + ")");
                }
                System.out.println();

                System.out.println("Primary Keys:");
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                while (primaryKeys.next()) {
                    String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
                    System.out.println("  " + primaryKeyColumn);
                }
                System.out.println();

                System.out.println("Foreign Keys:");
                ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
                while (foreignKeys.next()) {
                    String foreignKeyColumn = foreignKeys.getString("FKCOLUMN_NAME");
                    String referencedTable = foreignKeys.getString("PKTABLE_NAME");
                    String referencedColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                    System.out.println("  " + foreignKeyColumn + " -> " + referencedTable + "." + referencedColumnName);
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error accessing metadata: " + e.getMessage());
        }
    }
}
