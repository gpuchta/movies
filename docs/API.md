# API

## Hosts

    Staging: http://localhost:8080
    Production: http://10.0.0.13:7070

## Settings

### Retrieve

    GET http://localhost:8080/movies/settings
    
    {
      "environment": {
        "data_folder": "C:\\Users\\<username>\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\movies"
      },
      "settings": {
        "tmdb_api_key": null
      }
    }
    
### Persist

    PUT http://localhost:8080/movies/settings
    
    {
      "environment": {
        "data_folder": "C:\\Users\\<username>\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\movies"
      },
      "settings": {
        "tmdb_api_key": "<api-key>"
      }
    }

## Imdb

### Search

    http://localhost:8080/movies/tmdb/search?title=Ronin&year=1998
    
    [
      {
        "tmdb_id": 8195,
        "title": "Ronin",
        "overview": "A briefcase with undisclosed contents ? sought by Irish terrorists ...",
        "poster_path": "/UoFUXemMYGKuCN01Y111JlPjjr.jpg",
        "backdrop_path": "/tzAtSkpEyAftOuNqcShaBOxWIkw.jpg",
        "vote_average": 6.7,
        "vote_count": 489,
        "budget": 0,
        "revenue": 0,
        "genres": [],
        "directors": [],
        "actors": [],
        "posters": [],
        "backdrops": []
      },
      {
        ...
      }
    ]

### Details

    GET http://localhost:8080/movies/tmdb/detail/8195
    
    accept: application/json
    
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
   