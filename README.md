# BookingService

- name in gate way: booking-service
- port: 8093
- database : BookingDb
- path: /bookings/**

- When user PostRequest to "/add/"
  + This Service will call "matches/ticket/changeStatus/{matchId}/{ticketId}" of match-service
  + match-service will check infomation( mathId, ticketId). If infomation doesn't exist it'll return that customer can not book ticket to booking-service
  + If infomation  exist it will check ticketStatus. If ticketSatus is true, it will update ticketStatus and return that customer can book ticket to booking-service
  + If booking-service get message("Booked successfully") from math-service it will add new Booking to database 
