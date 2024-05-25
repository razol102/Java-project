package DTO.definition;

public class GridDTO {
    private final String rows, columns;

    public GridDTO(String rows, String columns) {
        this.rows = rows;
        this.columns = columns;
    }
    public String getGrid() {
        return rows + " X " + columns;
    }

    public String getColumns() {
        return columns;
    }

    public String getRows() {
        return rows;
    }
}
