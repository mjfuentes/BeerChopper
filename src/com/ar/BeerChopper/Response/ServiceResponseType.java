package com.ar.BeerChopper.Response;

public enum ServiceResponseType {

    /*ACKS*/

    OK("The operation completed without errors", false, 1),

    NOW_OPERATIVE_MESSAGE("Pylon is now operative", false, 1),

    /* Transiciones */

    NEW_ADMIN_TRANSACTION_READY("Correct admin identification", false, 0),
    NEW_CUSTOMER_TRANSACTION_READY("Correct client identification", false, 1),
    MEASURE_SELECTED("Measure selected correctly", false, 1),
    STARTING_TRANSACTION("Starting transaction", false, 1),
    CONSUMPTION_RECEIVED("Consumption received correctly", false, 1),
    TRANSACTION_FINISHED("Transaction finished correctly", false, 1),

    /* Checkeos */
    CHOPPER_IS_WAITING_FOR_GLASS("Chopper is ready and waiting for glass",false, 1),
    CHOPPER_IS_IN_TRANSACTION("Chopper is currently pouring beer",false, 1),
    CHOPPER_IS_READY("Chopper is ready for a new transaction",false, 1),

    /*Errores en checkeos*/
    NOT_EXIST_CHOPPER("Requested chopper is not configured", true, -1),
    NOT_EXIST_PYLON("Requested pylon is not configured", true, -1),
    NOT_EXIST_CARD("The card is not registered in the system", true, -1),
    NOT_ENOUGH_BEER("The're is not enough beer for this consumption", true, -1),
    NOT_ENOUGH_CREDIT("Client has not enough credit for this consumption", true, -1),
    NOT_CURRENT_CARD("The card was replaced in the system", true, -1),
    CARD_NOT_BINDED("The card is not asociated with a client", true, -1),
    CARD_WRONG_SETUP("The card belongs to other kind of user", true, -1),
    WRONG_CHOPPER_STATUS("The operation could not be performed due to actual transaction status", true, -1),
    WRONG_CHOPPER_MODE("This operation is not posible due to a bad configuration in the system", true, -1),
    EMPTY_PARTIAL_COMSUMPTION("Partial compsumtion with 0 pulses received", true, -1),
    CHOPPER_IS_WORKING("Chopper is already working thus, it cannot perform this action", true, -1),
    MULTIPLE_LOGIN("Multiple login for different Chopper or Pylon", true, -1),

    /*Errores en transiciones*/
    LOGIN_FAILED("The login operation failed", true, -1),
    PARTIAL_CONSUMPTION_FAILED("The consumption operation failed", true, -1),
    GLASS_DETECTED_FAILED("The glass detected operation failed", true, -1),
    WAIT_TIMEOUT_REACHED("The time for user glass detection or measure selection has expired", true, -1),

    SERVICE_NOT_IMPLEMENTED_YET("SERVICE_NOT_IMPLEMENTED_YET", true, -1);

    private String message;

    private boolean error;

    private long value;

    public String getMessage() {
        return message;
    }

    public ServiceResponse getAsServiceResponse() {
        return new ServiceResponse(this);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ServiceResponseType(String message, boolean error, long value) {
        this.message = message;
        this.error = error;
        this.setValue(value);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
