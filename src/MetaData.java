import java.sql.*;

public class MetaData {

    private Connection connection;

    public MetaData(Connection connection) {
        this.connection = connection;
    }

    public void displayTableInfo() {
        displayTableNames();
    }

    public void displayTableNames() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                System.out.println("Columns:");
                displayColumns(tableName);

                System.out.println("Primary Keys:");
                displayPrimaryKeys(tableName);

                System.out.println("Foreign Keys:");
                displayForeignKeys(tableName);

                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error accessing metadata: " + e.getMessage());
        }
    }

    private void displayColumns(String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                System.out.println("  " + columnName + " (" + dataType + ", Size: " + columnSize + ")");
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Error accessing columns metadata: " + e.getMessage());
        }
    }

    private void displayPrimaryKeys(String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            while (primaryKeys.next()) {
                String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
                System.out.println("  " + primaryKeyColumn);
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Error accessing primary keys metadata: " + e.getMessage());
        }
    }

    private void displayForeignKeys(String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
            while (foreignKeys.next()) {
                String foreignKeyColumn = foreignKeys.getString("FKCOLUMN_NAME");
                String referencedTable = foreignKeys.getString("PKTABLE_NAME");
                String referencedColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                System.out.println("  " + foreignKeyColumn + " -> " + referencedTable + "." + referencedColumnName);
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Error accessing foreign keys metadata: " + e.getMessage());
        }
    }
}
