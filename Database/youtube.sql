create database youtube_db;

SHOW DATABASES;

USE youtube_db;

select * from videos;

truncate table videos;



USE youtube_db;

USE youtube_db;

INSERT INTO videos
(id, title, channel, views, uploaded, duration, thumbnail, video_url, category, liked)
VALUES

(48,
 'Emraan Hashmi All Time Hits',
 'T-Series',
 '32M',
 '3 days ago',
 '10:25',
 'https://via.placeholder.com/300x170?text=Emraan+Hits',
 'https://www.youtube.com/results?search_query=Emraan+Hashmi+all+time+hits',
 'Recommended',
 false),

(49,
 'Emraan Hashmi Romantic Songs',
 'Sony Music India',
 '28M',
 '5 days ago',
 '12:15',
 'https://via.placeholder.com/300x170?text=Romantic+Songs',
 'https://www.youtube.com/results?search_query=Emraan+Hashmi+romantic+songs',
 'Recommended',
 false),

(50,
 'Jannat - Best Scenes',
 'Vishesh Films',
 '18M',
 '1 week ago',
 '8:30',
 'https://via.placeholder.com/300x170?text=Jannat+Scenes',
 'https://www.youtube.com/results?search_query=Jannat+Emraan+Hashmi+best+scenes',
 'Recommended',
 false),

(51,
 'Murder - Best Movie Scenes',
 'Vishesh Films',
 '15M',
 '1 week ago',
 '9:10',
 'https://via.placeholder.com/300x170?text=Murder+Scenes',
 'https://www.youtube.com/results?search_query=Murder+Emraan+Hashmi+best+scenes',
 'Recommended',
 false),

(52,
 'Tum Mile - Full Song',
 'Sony Music India',
 '42M',
 '2 weeks ago',
 '5:14',
 'https://via.placeholder.com/300x170?text=Tum+Mile',
 'https://www.youtube.com/results?search_query=Tum+Mile+Emraan+Hashmi',
 'Recommended',
 false),

(53,
 'Pee Loon - Love Song',
 'T-Series',
 '55M',
 '2 weeks ago',
 '4:45',
 'https://via.placeholder.com/300x170?text=Pee+Loon',
 'https://www.youtube.com/results?search_query=Pee+Loon+Emraan+Hashmi',
 'Recommended',
 false),

(54,
 'Emraan Hashmi Top 10 Movies',
 'Bollywood Classics',
 '21M',
 '3 weeks ago',
 '15:20',
 'https://via.placeholder.com/300x170?text=Top+10+Movies',
 'https://www.youtube.com/results?search_query=Emraan+Hashmi+top+10+movies',
 'Recommended',
 false),

(55,
 'Bollywood Romantic Movie Collection',
 'Bollywood Classics',
 '19M',
 '1 month ago',
 '11:40',
 'https://via.placeholder.com/300x170?text=Bollywood+Movies',
 'https://www.youtube.com/results?search_query=Emraan+Hashmi+romantic+movies',
 'Recommended',
 false);
 
 
 select * from videos;
 
 
 update videos set thumbnail = 'https://th.bing.com/th/id/OIP.2DzgYcnEjvaXfGBxWhkxXAHaEK?w=320&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3' where id = 47;
 