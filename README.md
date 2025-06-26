# secured rest api sb3

cd C:\HONG\JAVATest202504\TESTh3\spring-boot3-mvc-jpa-restApi

## run

./mvnw springboot:run  // run

## users

"user", "user", "ROLE_USER"    // user
"autor", "autor", "ROLE_AUTOR" // autor
"admin", "admin", "ROLE_USER", "ROLE_AUTOR", "ROLE_ADMIN" // admin, autor, user

# actions

## add a book 

POST http://localhost:8000/api/books

{
    "title": "Beginning Spring boot 3",
    "content": "so you must begin"
}

autor or admin // user role

## get all books

GET http://localhost:8000/api/books

authenticated user // all role

## books search by title

GET http://localhost:8000/api/books/search?title=Spring

authenticated user // all role

## add review to a book

POST http://localhost:8000/api/reviews/2

{
    "name":"gest", 
    "email": "gest@gmail.de", 
    "content":"i like it's Style",
    "likeStatus":"High"
}

authenticated user // user or admin role

# UI SERVER

## install UI app javascript

git clone https://github.com/hong1234/BookReviewReactUI_v2J.git

cd BookReviewReactUI_v2J

npm install

## run dev UI-server

npm start   



## building asserts

npm run build

## run prod UI-server

php -S localhost:3000 -t build/

## url in browser

http://localhost:3000/



