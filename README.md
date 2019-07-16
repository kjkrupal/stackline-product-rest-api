# Simple REST API

## Instructions to run
To run the application you will need docker installed on your computer. Once docker is installed and running, follow the following steps.

1. Extract the zip file
2. Change directory to stacklineapi
    ```
    cd stacklineapi
    ```
3. Enter the following command in terminal to build docker image
    ```
    docker build -f Dockerfile -t stackline-api .
    ```
4. Now run the docker image using following command

    ```
    docker run -p 8088:8088 stackline-api
    ```
Now use postman to make requests to the endpoints.

## Solution description

Since we are reading data from a file, I have used JAVA Streams to do processing on file. So all the operations on file should take atleast O(n) where n is the number of records present in the file. Also, the space complexity for all the endpoints would be O(n).

All the endpoints handle errors properly and returns a human-readable error message along with appropriate HTTP status code.

Sample error:
    
    {
        "message": "This is an error message"
    }

#### POST /api/products/autocomplete

For this request the time complexity is O(mn) where n number of records and m is length of prefix. The solution can be further optimized by using Rabin-Karn pattern matching algorithm which can give a complexity of O(m + n).

Request example:

    { "type": "brand", "prefix": "Can" }

Response example:

    ["Canon","Candyoo","Canless Air System","Cangshan","Canyon Dancer","Cannon Safe","Canonet","CandyHome","Canopy"]

Error handling:

- 400 if type or prefix is not provided
- 400 if value of type is not "category", "brand" or "name"

#### POST /api/products/search

For this request I have made an assumption that there can be only two conditions. One for "brandName" and one for "categoryName".

The time complexity is O(mn) where n is number of records and m is number of values for the condition.  

Request example:

    { "conditions": [
            { "type": "brandName", "values": ["Brother", "Canon"] },
            { "type": "categoryName", "values": ["Printers & Scanners"] }
      ],
      "pagination": { "from": 1, "size": 3 }
    }

Response example:

    [
      {
        "productId": "B01DMYYCD6",
        "title": "Canon EOS 80D DSLR Camera with EF-S 18-55mm f/3.5-5.6 IS STM Lens, Total Of 48GB SDHC along with Deluxe accessory bundle",
        "brandId": "4534",
        "brandName": "Canon",
        "categoryId": "176",
        "categoryName": "Cameras"
        },
      {
        "productId": "B001EQSFRE",
        "title": "Canon Staples P1 (2x5000)",
        "brandId": "4534",
        "brandName": "Canon",
        "categoryId": "191",
        "categoryName": "Home Audio"
      },
      {
        "productId": "B004H9PAO6",
        "title": "Brother TN330 Toner, Standard Yield (Reseller Offer)",
        "brandId": "4053",
        "brandName": "Brother",
        "categoryId": "200",
        "categoryName": "Printers & Scanners"
      }
    ]

Error handling:

- 400 if conditions or pagination is not provided
- 400 if "size" or "from" value in pagination is less than or equal to 0.
- 404 if value of "from" is greater than number of pages formed.

#### POST /api/products/keywords

For this request the time complexity is O(mnk) where n is number of records and m is number of keywords provided. and k is number of words present in a product title.

Request example:

    { "keywords": ["toner", "ink" ] }

Response example:

    { "keywordFrequencies": [
        { "toner", "25" },
        { "ink", "15" }
      ]
    }

Error handling:
- 400 if keywords is null
- 400 if list of keywords is an empty list