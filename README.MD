# Relearning Clojure one step at a time

## Tech
- [Clojure](https://clojure.org/)
- [Compojure](https://github.com/weavejester/compojure) for routing
- [http-kit](https://github.com/http-kit/http-kit) as HTTP server
- [Hiccup](https://github.com/weavejester/hiccup) as HTML renderer
- [Atom](https://clojure.org/reference/atoms) as in-memory database
- [environ](https://github.com/weavejester/environ) for fetching environment variables

## Running the application locally
- set the PORT environment variable to your chosen port
  - `export PORT=3000`
- execute the `run-server` alias
  - `clj -A:run-server`
- the application will echo out it's URL
  - `Server running at http://localhost:3000`