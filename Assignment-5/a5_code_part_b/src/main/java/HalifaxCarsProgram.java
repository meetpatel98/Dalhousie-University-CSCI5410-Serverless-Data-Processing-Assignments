import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class HalifaxCarsProgram {
    StringBuilder vehicleOrderMessage = new StringBuilder();
    public void processingVehicleOrder() {
        System.out.print("\n Halifax Cars (Car Rental Compnay)\n\n");
        AmazonSQS amazonSQSClientBuilder = new AWSConnection().createAmazonSQSClientBuilder();
        String SQSQueue = amazonSQSClientBuilder.getQueueUrl(new GetQueueUrlRequest().withQueueName("HalifaxCarsSQS")).getQueueUrl();
        int orderBatch = 0;
        while (true) {
            System.out.print("--> Order Batch: " + orderBatch++ + "\n\n");
            for (int i = 1; i <= new Random().nextInt(5) + 2; ++i) {
                List<VehicleListModel> haliifaxCarsList = VehicleData.getVehicleList();
                OrderModel orderModel = new OrderModel(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                orderModel.addVehicleListModels(haliifaxCarsList.get(new Random().nextInt(haliifaxCarsList.size())));
                StringBuilder sb = new StringBuilder();
                sb.append("Order id: ").append(UUID.randomUUID()).append("\n");
                for (int i1 = 0; i1 < orderModel.getVehicleListModels().size(); ++i1) {
                    sb.append("Vehicle Type: ").append(orderModel.getVehicleListModels().get(i1).getVehicleType()).append("\n");
                    sb.append("Pick Up Dates: ").append(orderModel.getVehicleListModels().get(i1).getPickUpDate()).append("\n");
                    sb.append("Pick Up Time: ").append(orderModel.getVehicleListModels().get(i1).getPickUpTime()).append("\n");
                    sb.append("Vehicle Features: ").append(orderModel.getVehicleListModels().get(i1).getVehicleDescription()).append("\n\n");
                }
                vehicleOrderMessage.append(sb);
            }
            System.out.print(vehicleOrderMessage);
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(SQSQueue)
                    .withMessageBody(vehicleOrderMessage.toString());
            System.out.print("- Order from batch " + orderBatch + " posted: " + amazonSQSClientBuilder.sendMessage(sendMessageRequest).getMessageId() + "\n\n");
            try {
                System.out.print("Waiting for Next orders...\n\n");
                Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
