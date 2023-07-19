package stepDefinition;

import implementation.DemoImplementation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefinition {
    @Given("^Get the booking ids$")
    public void getBookingId()
    {
        DemoImplementation.getMethod();
    }
    @Then("^Post the json in the url$")
    public void postBooking()
    {
        DemoImplementation.postMethod();
    }
    @Then("^use patch method to change the first and last name$")
    public void patchMethod()
    {
        DemoImplementation.patchMethodForBooking();
    }
    @Then("^use Put method to change the data of id$")
    public void putData(){
        DemoImplementation.putMethod();
    }
    @And("^Delete the changed data$")
    public void deleteMethod()
    {
        DemoImplementation.deleteMethodForBooking();
    }
}
