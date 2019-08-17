# MovieDB API

MovieDB provides an API used by its web pages to query and modify the
data stored in an HSQL database.

## Hosts and Ports

```
Staging: http://localhost:8080
Production: http://xxx.xxx.xxx.xxx:7070
```

### Retrieve Settings

```
GET http://localhost:8080/movies/settings
```
```Json
{
  "environment": {
    "data_folder": "C:\\Users\\<username>\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\movies"
  },
  "settings": {
    "tmdb_api_key": "<api-key>"
  }
}
```

### Persist Settings

```
PUT http://localhost:8080/movies/settings
```
```Json
{
  "environment": {
    "data_folder": "C:\\Users\\<username>\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\movies"
  },
  "settings": {
    "tmdb_api_key": "<api-key>"
  }
}
```

## MovieDB/TMDb API

### Search

```
http://localhost:8080/movies/tmdb/search?title=Ronin&year=1998
```
```Json
[
  {
    "tmdb_id": 8195,
    "title": "Ronin",
    "year": 1998,
    "overview": "A briefcase with undisclosed contents â€“ sought by Irish terrorists and the Russian mob â€“ makes its way into criminals\u0027 hands. An Irish liaison assembles a squad of mercenaries, or \u0027ronin\u0027, and gives them the thorny task of recovering the case.",
    "poster_path": "/UoFUXemMYGKuCN01Y111JlPjjr.jpg",
    "poster_data_uri": null,
    "backdrop_path": "/l1OwGq9f2ZzlFJ35tP6mhhDqkMw.jpg",
    "vote_average": 6.9,
    "vote_count": 1128,
    "budget": 0,
    "revenue": 0,
    "genres": [],
    "directors": [],
    "actors": [],
    "keywords": [],
    "posters": [],
    "backdrops": [],
    "locator": null,
    "has_details": false
  }
]
```

### Details

```
GET http://localhost:8080/movies/tmdb/detail/8195
```
```Json
{
  "tmdb_id": 8195,
  "title": "Ronin",
  "overview": "A briefcase with undisclosed contents ? sought by Irish terrorists ...",
  "poster_path": "/UoFUXemMYGKuCN01Y111JlPjjr.jpg",
  "backdrop_path": "/tzAtSkpEyAftOuNqcShaBOxWIkw.jpg",
  "vote_average": 6.7,
  "vote_count": 491,
  "budget": 55000000,
  "revenue": 41610884,
  "genres": [
    "Action",
    "Thriller",
    "Crime",
    "Adventure"
  ],
  "directors": [
    "John Frankenheimer"
  ],
  "actors": [
    "Robert De Niro",
    "Jean Reno",
    "Natascha McElhone",
    "Stellan Skarsgård",
    "Sean Bean",
    "Jonathan Pryce",
    "Skipp Sudduth"
  ],
  "posters": [
    "/xhJ2hKDNMmdVpaiDz7pZEoi2FL.jpg",
    "/vnevNzWAmy5xG3EjBxQQiMMHr05.jpg",
    "/jw4SbehUSkWQZ2znDHEXUO9g6ZT.jpg",
    "/iCUrEEJodVdJ8FRPVSt5J40z8LW.jpg",
    "/pMOEwd1lDh8G5xfkOsv1zfjx1hT.jpg",
    "/nxOlyHph9cc2LqDmwv4TmlKvhGg.jpg",
    ...
  ],
  "backdrops": [
    "/tzAtSkpEyAftOuNqcShaBOxWIkw.jpg",
    "/3YXpg4403uXQXUem33foedRa28C.jpg",
    "/jnlDMWZG8wfsdtC2VBQLU7BcHyZ.jpg",
    ...
  ]
}
```

# TMDb API

## Reference

https://developers.themoviedb.org/3/getting-started/search-and-query-for-details

## Queries

This application uses TMBb API version 3.

1) Search by title, then filter by precise title and year.
    
    https://api.themoviedb.org/3/search/movie?api_key=API_KEY&query=2012

2) Take the id ( e.g. "id": 14161 ) and fetch the videos
  
    http://api.themoviedb.org/3/movie/14161/videos?api_key=API_KEY

    ```
    {
      "id": 14161,
      "results": [
        {
          "id": "533ec676c3a36854480026c8",
          "iso_639_1": "en",
          "iso_3166_1": "US",
          "key": "sFXGrTng0gQ",
          "name": "Trailer 1",
          "site": "YouTube",
          "size": 720,
          "type": "Trailer"
        }
      ]
    }
    ```
    
3) Construct preview link (TODO)
      
    (2012)
    
    Video: https://www.youtube.com/watch?v=sFXGrTng0gQ

    https://api.themoviedb.org/3/movie/550?api_key=API_KEY
