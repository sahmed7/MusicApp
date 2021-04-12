MUSICAPP README FILE

![img.png](img.png)

POST == http://localhost:9093/auth/users/register  ==  Register a user           == PUBLIC
POST == http://localhost:9093/auth/users/login     ==  Logs a user in            == PUBLIC
GET  == http://localhost:9093/api/genres       ==  Lists all genres      == PRIVATE
GET  == http://localhost:9093/api/genres/{id}     ==  Gets a single genre with the supplied id == PRIVATE
POST == http://localhost:9093/api/genres  ==  Creates a new genre == PRIVATE
PUT == http://localhost:9093/api/genres/{id}  ==  Updates a genre with the supplied id == PRIVATE
DELETE == http://localhost:9093/api/genres/{id}  ==  Deletes a genre with the supplied id == PRIVATE
POST == http://localhost:9093/api/genres/{id}/songs  ==  Creates a new song in the given genre == PRIVATE
GET == http://localhost:9093/api/genres/{id}/songs  ==  List all songs in the given genre == PRIVATE
PUT == http://localhost:9093/api/genres/{id}/songs/{id}  ==  Updates a song in the given genre == PRIVATE
DELETE == http://localhost:9093/api/genres/{id}/songs/{id}  ==  Deletes a song in the given genre == PRIVATE
