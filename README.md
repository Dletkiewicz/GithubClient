
# Github API 
Application built using https://developer.github.com/v3 as a backing API.

Application was created to list user repositories in following syntax:

Repository Name

Owner Login

For each branch it’s name and last commit sha.


Application handles situations when there isn't such username or when there is try to give header “Accept: application/xml”.





## Authors

- [@Dletkiewicz](https://www.github.com/dletkiewicz)


## Features

- Listing user repositories with branches and commit names
- Handling unwanted situations


## API Reference

#### Get all repositories

```http
  GET /api/v1/github-details/{username}
```



## Tech Stack

**Client:** Java, Spring Boot,

**Server:** Tomcat


## Screenshots

![App Screenshot](https://i.imgur.com/AMoyFnH.png)

![App Screenshot](https://i.imgur.com/5l0EyPx.png)

![App Screenshot](https://i.imgur.com/CxLu984.png)

