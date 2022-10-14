public class VehicleListModel {
    private final String vehicleType;
    private final String vehicleDescription;
    private final String pickUpDate;

    private final String pickUpTime;

    public VehicleListModel(String vehicleType, String vehicleDescription, String pickUpDate, String pickUpTime) {
        this.vehicleType = vehicleType;
        this.vehicleDescription = vehicleDescription;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
    }

    public String getVehicleType() {
        return vehicleType;
    }
    public String getVehicleDescription() {
        return vehicleDescription;
    }
    public String getPickUpDate() {
        return pickUpDate;
    }

    public String getPickUpTime(){
        return pickUpTime;
    }
}
