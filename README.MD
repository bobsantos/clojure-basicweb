# Relearning Clojure one step at a time

## First Trial
### Tech
- [Clojure](https://clojure.org/)
- [Compojure](https://github.com/weavejester/compojure) as router
- [http-kit](https://github.com/http-kit/http-kit) as HTTP server
- [Hiccup](https://github.com/weavejester/hiccup) as HTML renderer
- [Atom](https://clojure.org/reference/atoms) as in-memory database
- [environ](https://github.com/weavejester/environ) for fetching environment variables

## Second Trial
### Tech
- [ClojureScript](https://clojurescript.org/)
- [shadow-cljs](http://shadow-cljs.org/) as compiler
- [secretary](https://github.com/clj-commons/secretary) as router

## Running the application locally
### Backend
- set the PORT environment variable to your chosen port
  - `export PORT=3000`
- execute the `run-server` alias
  - `clj -A:run-server`
- the application will echo out it's URL
  - `Server running at http://localhost:3000`
 
### Frontend
- execute `yarn watch`
- the application will echo out where you can access the site and the build server
  - `HTTP server available at http://localhost:3001`
  - `server version: 2.10.14 running at http://localhost:9630`

## Deployment
### Backend
Heroku
https://www.gertgoet.com/2019/02/06/deploying-a-tools-deps-clojure-project-to-heroku.html
