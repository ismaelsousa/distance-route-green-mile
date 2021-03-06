
<p align='center'>
  <h1 align='center'>Throw an event when the driver moves away from transportation</h1>
 <img src='https://user-images.githubusercontent.com/28990749/102723069-6de4e600-42ba-11eb-965f-e820d4af9b30.png'/>
</p>

# Link to [Video](https://drive.google.com/file/d/1Bc3mNn3ICHl9qxEheRoLu8bH-6IRSg55/view?usp=sharing)

## Route

- From the first stop the mobile is away until the last coordinate at which it returns to transport

<p align='center'>
 <img src='https://user-images.githubusercontent.com/28990749/102723107-b8fef900-42ba-11eb-8e4f-001f89da5ae0.png'/>
</p>

## Create Route 

**POST: localhost:8080/routes**
```json
{
    "id": 10000,
    "name": "Route Test",
    "datePlans": 1607525341441,
    "equipment": {
        "id": 10000,
        "type": "TRACK"
    },
	 "mobileEquipment": {
      "id": 13,
      "type": "MOBILE"
    },
    "stops": [{
        "id": 1,
        "address": "Rua Padre joaquim de menezes, 976",
        "latitude": -5.0720683,
        "longitude": -37.9894671
    },{
        "id": 2,
        "address": "Rua Veriador efisio costa, 01",
        "latitude": -5.0747838,
        "longitude":-37.9908696
    },{
        "id": 4,
        "address": "Last ~> Rua Veriador efisio costa, 1027",
        "latitude": -5.0775801,
        "longitude":-37.9848137
    }]
}

```

## Running 

- install deps springboot - Marvem
- run RouteApplication
- When print `Iniciate send coordinates`, create route with item above

## Logs

<p align='center'>
 <img src='https://user-images.githubusercontent.com/28990749/102723122-da5fe500-42ba-11eb-941d-a0ae49786c54.png'/>
</p>


