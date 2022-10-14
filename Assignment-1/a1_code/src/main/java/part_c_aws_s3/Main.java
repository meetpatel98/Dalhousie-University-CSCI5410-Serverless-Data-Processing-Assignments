package part_c_aws_s3;

public class Main {
    public static void main(String[] args) {

        DynamoDBConnection dynamoDBConnection = new DynamoDBConnection();
        dynamoDBConnection.createDynamoDBClientBuilder();

        System.out.println("\n");
        System.out.println("Table Content before adding types_of_Campsites attribute");
        System.out.println("\n");
        RetriveData retriveBefore = new RetriveData();
        retriveBefore.retriveTableContent("Parks_NovaScotia");

        System.out.println("\n");
        System.out.println("------------------------------------------------------------");
        System.out.println("\n");

        System.out.println("Table Content after adding types_of_Campsites attribute");
        System.out.println("\n");
        AddAttribute addAttribute = new AddAttribute();
        addAttribute.createNewAttibute("Parks_NovaScotia");
        RetriveData retriveAfter = new RetriveData();
        retriveAfter.retriveTableContent("Parks_NovaScotia");
    }
}
