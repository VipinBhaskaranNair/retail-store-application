###################################
Problem Statement
###################################

On a retail website, the following discounts apply:
1.	If the user is an employee of the store, he gets a 30% discount
2.	If the user is an affiliate of the store, he gets a 10% discount
3.	If the user has been a customer for over 2 years, he gets a 5% discount.
4.	For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45 as a discount).
5.	The percentage based discounts do not apply on groceries.
6.	A user can get only one of the percentage based discounts on a bill.

Write a program in a Java – Spring boot with test cases such that given a bill, it finds the net payable amount. 

############################
Assumptions & Presumptions
############################
1) Database part is not included in code to keep the implementation simple and concentrate more on the problem statement.
2) User interface will be able to call the REST endpoint with the required details(Customer details, product information).
3) Discount percentages are externalised to property file
4) Bean validation is implemented for all required fields
5) Sonar report is generated from a server configured in local
6) Server port is changed to 8091




############################
Solution
############################
A Springboot java project is created which exposes an endpoint which will accept the shopping cart details as JSON request and returns the discounted invoice with discounted amount.

Sample Request:

URL: POST api/discountedInvoice

{
    "cartId":"C111",
    "customerDetails" : {
        "customerId": "CU001",
        "customerName": "Tom",
        "customerType":"AFFILIATE",
        "customerSince" : "2007-12-03T10:15:30"
    },
    "shoppingDate" : "2024-06-15T10:15:30",
    "cartItems" : [
        {
            "product": {
                "productId":"P001",
                "productName": "iPhone 12 Pro",
                "productType":  "ELECTRONICS",
                "unitPrice": "120.00d"
            },
            "quantity": 1
        },
        {
            "product": {
                "productId":"P002",
                "productName": "Onion",
                "productType":  "GROCERY",
                "unitPrice": "2.00d"
            },
            "quantity": 2
        }

    ]
}



Sample Response:

{
    "cartId": "C111",
    "customerDetails": {
        "customerId": "CU001",
        "customerName": "Tom",
        "customerType": "AFFILIATE",
        "customerSince": "2007-12-03T10:15:30"
    },
    "shoppingDate": "2024-06-15T10:15:30",
    "cartItems": [
        {
            "product": {
                "productId": "P001",
                "productName": "iPhone 12 Pro",
                "productType": "ELECTRONICS",
                "unitPrice": 120.0
            },
            "quantity": 1.0
        },
        {
            "product": {
                "productId": "P002",
                "productName": "Onion",
                "productType": "GROCERY",
                "unitPrice": 2.0
            },
            "quantity": 2.0
        }
    ],
    "discountedCartAmount": 107.0
}



################################
Build and Coverage Report
################################

JaCoCo is used for generating coverage report. Please follow below steps for generating report.

1) On running 'mvn clean install' will build, run tests and generate jacoco.exec file
2) On running 'mvn jacoco:report' will generates report in html file under target/site/jacoco directory.
