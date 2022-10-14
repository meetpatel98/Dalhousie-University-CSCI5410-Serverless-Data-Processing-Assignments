import java.util.ArrayList;
import java.util.List;

public class VehicleData {

    public static List<VehicleListModel> getVehicleList() {

        List<VehicleListModel> vehicleList = new ArrayList<>();

        vehicleList.add(new VehicleListModel("Small Pickup Truck",
                "Automatic TransmissionAir Conditioning" +
                        "| Air Bags | AM/FM Stereo", "25 July 2022", "12:00 PM"));

        vehicleList.add(new VehicleListModel("Mid Size SUV",
                " Air Bags | AM/FM Stereo " +
                        "| Touchscreen infotainment system | Panoramic Sunroof", "30 July 2022", "8:00 AM"));

        vehicleList.add(new VehicleListModel("Large Pickup Truck",
                "Touchscreen infotainment system | Panoramic Sunroof " +
                        "| Apple CarPlay and Android Auto | Music System", "05 August 2022", "7:00 PM"));

        vehicleList.add(new VehicleListModel("7 Passenger Minivan",
                "Apple CarPlay and Android Auto | Music System" +
                        "| Keyless Entry | Push Button Start/Stop", "10 August 2022", "9:00 PM"));


        vehicleList.add(new VehicleListModel("Full-Size SUV",
                "Keyless Entry | Push Button Start/Stop " +
                        "| Fast USB Charging Outlets | Wireless Charger", "15 August 2022", "7:30 PM"));


        vehicleList.add(new VehicleListModel("Convertible",
                "Fast USB Charging Outlets | Wireless Charger " +
                        "| Front and rear parking sensors", "20 August 2022", "2:00 PM"));


        vehicleList.add(new VehicleListModel("Coupe",
                "Front and rear parking sensors " +
                        "| Lane-departure warning", "25 August 2022", "1:00 PM"));

        vehicleList.add(new VehicleListModel("Cargo Van",
                "Lane-departure warning | Air Conditioning " +
                        "| Air Bags | Power front passenger seat", "30 August 2022", "3:30 PM"));
        return vehicleList;
    }
}
