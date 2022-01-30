# birthdaymatch

Match your birthday with any actor or actress

# TODO

- ~~security update~~
  - ~~put applicationsSecrets in gitignore~~
  - ~~find how to get info from applicationsSecrets~~
- add birthday match endpoint
  - ~~match birthday to get code for actor~~
      - ~~create exception handler~~ 
        - ~~find out how to handle exception~~ 
      - ~~study responseStatusExceptions~~
      - ~~study error of not finding properties~~
      - ~~fix mapping~~
      - ~~add test endpoint~~
  - get bio from name
    - add getBio service
      - recieve code
      - assemble request
      - send request
      - recieve response
      - map response to proper object
      - return mapped response
  - add birthdaymatch BO
  - add birthdaymatch controller
- create tests

- add other functinalities