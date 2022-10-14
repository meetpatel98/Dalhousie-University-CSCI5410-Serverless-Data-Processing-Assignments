import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private List<VehicleListModel> vehicleListModels;
    private List<String> dates;

    private List<String> times;

    public OrderModel(ArrayList<VehicleListModel> vehicleListModels, ArrayList<String> dates, ArrayList<String> times) {
        this.vehicleListModels = vehicleListModels;
        this.dates = dates;
        this.times = times;
    }
    public void addVehicleListModels(VehicleListModel vehicleListModel){
        vehicleListModels.add(vehicleListModel);
    }
    public List<VehicleListModel>getVehicleListModels(){
        return vehicleListModels;
    }

    public void addInDates(String date){
        dates.add(date);
    }

    public List<String> getDates() {
        return dates;
    }

    public void addInTime(String time){
        dates.add(time);
    }

    public List<String> getTimes() {
        return times;
    }
}
