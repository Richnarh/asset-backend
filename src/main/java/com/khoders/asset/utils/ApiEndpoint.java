package com.khoders.asset.utils;

public class ApiEndpoint {
    public static final String BASE_API = "api/";
    public static final String VERSION = "v1/";

    public static final String AUTH_ENDPOINT = BASE_API + "auth";

    public static final String COMPANY_ENDPOINT = BASE_API + VERSION + "company";
    public static final String ACCOUNT_ENDPOINT = BASE_API + VERSION + "accounts";
    public static final String LOOKUP_ENDPOINT = BASE_API + VERSION + "data";
    public static final String BILL_ENDPOINT = BASE_API + VERSION + "bills";
    public static final String ASSET_TRANSFER_ENDPOINT = BASE_API + VERSION + "transfers";
    public static final String EMPLOYEE_ENDPOINT = BASE_API + VERSION + "employees";
    public static final String VENDOR_ENDPOINT = BASE_API + VERSION + "business-clients";
    public static final String CATEGORY_ENDPOINT = BASE_API + VERSION + "category";
    public static final String DEPARTMENT_ENDPOINT = BASE_API + VERSION + "department";
    public static final String INVENTORY_ENDPOINT = BASE_API + VERSION + "inventory";
    public static final String LOCATION_ENDPOINT = BASE_API + VERSION + "location";

    public static final String ASSET_DISPATCH_REQ_ENDPOINT = BASE_API + VERSION + "dispatch-requests";
    public static final String MAINTENANCE_REQ_ENDPOINT = BASE_API + VERSION + "maintenance-request";
}
