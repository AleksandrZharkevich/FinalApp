package by.mrbregovich.iba.project.entity;

/*
 * REGISTERED - request is registered, do not processed by manager
 * IN_PROCESS - request is registered, processed by manager
 * DONE - manager processed this request
 */
public enum RequestStatus {
    REGISTERED, IN_PROCESS, DONE
}
