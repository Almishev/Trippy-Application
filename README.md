                                                 Trippy
                                                  from
                                              Anton Almishev
About The Project:

This project is to build Trippy application.
Trippy allows users to see lists of hotels, bars and restaurants in a city of their choice,
or from all within the app. Each venue should have detailed information such as type, reviews, rating.
Additional establishments can be added by any one to be viewed by other users. Furthermore, users should be able to leave reviews, 
add users and access any user’s public information such as username, join date.

My diagram is in the PNG file in folder resources.

The API is available at http://localhost:8081

Endpoints:

Folder TOWNS
List of towns ->  GET /towns -> Returns a list of towns.
Get a single town -> GET /towns/townId -> Retrieve detailed information about a town.
Add a town -> POST /towns -> The request body needs to be in JSON format and includes TownRequest.
Update a town -> PUT /towns/townId -> It needs  @RequestBody @Valid TownRequest, @PathVariable int id
Delete a town -> DELETE /towns/townId -> It needs @PathVariable int townId

Folder USERS
List of users -> GET /users -> Returns a list of users.
Get a single user -> GET /users/userId -> Retrieve detailed information about  user.
Add  user  -> POST /users -> The request body needs to be in JSON format and includes UserRequest.
Update user -> PUT /users/userId -> It needs  @RequestBody @Valid UserRequest, @PathVariable int id
Delete user -> DELETE /users/userId -> It needs @PathVariable int userId, in SQL it's maiden with cascade.

Folder ESTABLISHMENTS
List of establishments -> GET /ests -> Returns a list of establishments, if you want to limit, it has @RequestParam(name = "limit", defaultValue = "100000") int limit.
Get a single establishments -> GET /ests/estId -> Retrieve detailed information about establishments.
Add  establishments  -> POST /ests -> The request body needs to be in JSON format and includes EstablishmentsRequest.
Update establishment -> PUT /ests/estId -> It needs  @RequestBody @Valid EstablishmentRequest, @PathVariable int estId
Delete establishment -> DELETE /ests/id -> It needs @PathVariable int id, inn SQL it's maiden with cascade.

Folder COMMENTS
List of comments ->  GET /comments -> Returns a list of comments.
Get a single comments -> GET /comments/id -> Retrieve detailed information about a comment.
Add a comment -> POST /comments -> The request body needs to be in JSON format and includes CommentRequest.
Update a comment -> PUT /comments/commentId -> It needs  @RequestBody @Valid CommentRequest ,@RequestParam int userId, @PathVariable int commentId
This userId I need for security- only author of the comment can update , or delete it.
Delete a comment -> DELETE /comments/commentId -> It needs @PathVariable int commentId, @RequestParam int userId

Folder VIEWS
List of common records -> GET /views -> Returns a list of records with info for  town,type,company,totalRating
You can get it with limit -> @RequestParam(name = "limit", defaultValue = "100000") int limit.
List of rate records -> GET /rate -> Returns a list of records with info for type ,company,rate.
List of town records -> GET /views/v1 -> Returns a list of records with info for type,name of the company,avg rating, total comments.
It has @RequestParam String town.
List of type records -> GET /views/v2 -> Returns a list of records with info for town,name of the company,avg rating.
It has @RequestParam String type.
List of comments for establishments by name -> GET /content -> Returns a list of records with info for 
type of business, name of the company ,avg rating.
It has @RequestParam String town, @RequestParam double rate, this allowing us to select town and min avg rate.

Built With:
This project was maiden with PostGreSql and Java with Spring boot framework.

Installation:

Clone the repo sh git clone https://github.com/ScalefocusAcademy2023/anton-almishev

Tricky methods:

I don't have security dependency , I made it secured (for example : only the  user that wrote review has authority to delete it , or edit the review) 
with simple if checking :

@DeleteMapping("/comments/{commentId}")
public ResponseEntity<CommentDto> deleteComment(@PathVariable int commentId, @RequestParam int userId,
@RequestParam(required = false) boolean returnOld) {
if (userId == commentService.getUserIdFromTheComment(commentId)) {
CommentDto commentDto = commentService.removeComment(commentId);
return returnOld ? ResponseEntity.ok(commentDto) : ResponseEntity.noContent().build();
}else{
throw new NotFoundException("It's allowed only to owner of the comment");
  }
}

The problem - We can't add establishments that are added I solved this with boolean method: isInTheData

I don't have pagination  for method getAll (What happen if we have got 50 k records in the data), 
but I resolved with LIMIT ? in my sqlQuery, and adding where clause -> where id> ? and id < ? .
@GetMapping("/ests/limit/{limit}")
public ResponseEntity<List<Establishment>> getAllEstablishments(@PathVariable int limit) {
List<Establishment> establishments = estService.getAllEstablishments(limit);
return ResponseEntity.ok(establishments);
}

What my app can do:

CRUD for Towns, Establishments,Comments,Users
Get methods for  seeing lists of hotels, bars and restaurants in a city of their choice.
And sorting by their choice.

Road map:

Right now I believe this project is completed.It's working on Postman, but in the future I'm going to use thymeleaf 
and with HTML/CSS 
the project will be more attractive.

Contact:
Git Hub: https://github.com/ScalefocusAcademy2023/anton-almishev
Email: antonalmishe@abv.bg
Phone: 0877382224