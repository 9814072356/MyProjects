package experiment;

public class ShipComponent {
    private ShipComponent[] adjacentComponents;

    public ShipComponent() {
        adjacentComponents = new ShipComponent[4];
    }

    public void setAdjacentComponent(int index, ShipComponent component) {
        adjacentComponents[index] = component;
        component.adjacentComponents[(index + 2) % 4] = this;
    }

    public ShipComponent getAdjacentComponent(int index) {
        return adjacentComponents[index];
    }
}
