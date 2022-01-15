# daollar

The project is implemented using standard spring boot starter kit. The given json files are under resources folder which are initialized while running the project. Standard controller, manager, dao etc. folder structure is used to store the project. Also, used standard naming conventions too. Data is stored and initialized inside a variable in DAOs. 

- In order to run the project run the command: 
  ## mvn clean package && java -jar target/daollar-0.0.1-SNAPSHOT.jar
  
- API for getting all the transactions:
  ## localhost:8080/daollar/get-transactions?direction=<DESC/ASC>&pageNo=<PAGE_NO>

- API for getting all the installments corresponding to the transactions
  ## localhost:8080/daollar/get-installments?parentId=<PARENT_ID>
