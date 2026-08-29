package com.raunak.youtube.api.service;

import com.raunak.youtube.api.model.Video;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

        private final List<Video> videos = new ArrayList<>();

        public VideoService() {

                // =====================================================
                // VIDEO 1
                // =====================================================

                videos.add(new Video(
                                1,
                                "Jannat Full Movie | Emraan Hashmi",
                                "T-Series Movies",
                                "25M views",
                                "2 years ago",
                                "2:08:19",
                                "https://cdn.cinematerial.com/p/297x/iajmjaap/jannat-indian-movie-poster-md.jpg?v=1456298328",
                                "recommended",
                                "https://youtu.be/rdDSok6g1LI?si=g5i3DvlkaO555qY0"));

                // =====================================================
                // VIDEO 2
                // =====================================================

                videos.add(new Video(
                                2,
                                "Aashiq Banaya Aapne | HD Video Song",
                                "Bollywood Hits",
                                "120M views",
                                "4 years ago",
                                "2:05:33",
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS42lvnHL2Hnqnjocbv1Q9vMdhVQEsehl34HcLcrfSHyg&s=10",
                                "recommended",
                                "https://www.youtube.com/embed/VIDEO_ID_2"));

                // =====================================================
                // VIDEO 3
                // =====================================================

                videos.add(new Video(
                                3,
                                "Murder (2004) Best Scenes",
                                "Movie Clips",
                                "15M views",
                                "1 year ago",
                                "2:04:12",
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvSOC3si4jKR47IV6FprMm03f3dcYL5GoGmZEZQvebmg&s=10",
                                "recommended",
                                "https://www.youtube.com/embed/VIDEO_ID_3"));

                // =====================================================
                // VIDEO 4
                // =====================================================

                videos.add(new Video(
                                4,
                                "Hamari Adhuri Kahani | Official Trailer",
                                "Movie Trailers",
                                "45M views",
                                "5 years ago",
                                "2:09:33",
                                "https://picsum.photos/300/180",
                                "recommended",
                                "https://www.youtube.com/embed/VIDEO_ID_4"));

                // =====================================================
                // VIDEO 5
                // =====================================================

                videos.add(new Video(
                                5,
                                "Emraan Hashmi Exclusive Interview",
                                "Film Companion",
                                "3.5M views",
                                "6 months ago",
                                "45:19",
                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXip1tYlrJ4pef4ugmcjnHD7na_C_I8Eia4FzfLFLU6Q&s=10",
                                "recommended",
                                "https://www.youtube.com/embed/VIDEO_ID_5"));

                // =====================================================
                // VIDEO 6
                // =====================================================

                videos.add(new Video(
                                6,
                                "To phir Aao Song",
                                "Vishesh Films",
                                "101M views",
                                "2 months ago",
                                "05:17",
                                "https://th.bing.com/th/id/OIP.k1V3mVsjP0pYUZnOzcvTlQHaEK?w=271&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                                "trending",
                                "https://www.youtube.com/embed/VIDEO_ID_6"));

                // =====================================================
                // VIDEO 7
                // =====================================================

                videos.add(new Video(
                                7,
                                "Hale Dil",
                                "Murder 2",
                                "284M views",
                                "10 years ago",
                                "04:49",
                                "https://th.bing.com/th/id/OIP.tdBI_FTDeRU-NCZe9yZO_gHaEK?w=320&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                                "music",
                                "https://www.youtube.com/embed/VIDEO_ID_7"));

                // =====================================================
                // VIDEO 8
                // =====================================================

                videos.add(new Video(
                                8,
                                "Lutt Gaye",
                                "Music Album",
                                "680M views",
                                "3 years ago",
                                "04:00",
                                "https://tellyflight.com/wp-content/uploads/2021/02/LUT-GAYE-SONG-CAST-AND-LYRICS-copy.jpg",
                                "music",
                                "https://www.youtube.com/embed/VIDEO_ID_8"));

                // =====================================================
                // VIDEO 9
                // =====================================================

                videos.add(new Video(
                                9,
                                "Tumhare liye",
                                "Music Album",
                                "4.5M views",
                                "11 Hours ago",
                                "03:28",
                                "https://i.ytimg.com/vi/8Mj-MjMFDxc/hqdefault.jpg?sqp=-oaymwEnCNACELwBSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLAA3Ty-5NFd2HmJo-tjmKFLrXrGfQ",
                                "music",
                                "https://www.youtube.com/embed/VIDEO_ID_9"));

                // =====================================================
                // VIDEO 10
                // =====================================================

                videos.add(new Video(
                                10,
                                "Raaz The Mystery Continues",
                                "Raaz 2",
                                "145M views",
                                "8 months ago",
                                "2:17:13",
                                "https://www.bing.com/th/id/OIP._8PqTx9T1g9OUQ_4Rf5oFgHaJ4?w=193&h=257&c=8&rs=1&qlt=90&o=6&dpr=1.3&pid=ImgAns&rm=2",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_10"));

                // =====================================================
                // VIDEO 11
                // =====================================================

                videos.add(new Video(
                                11,
                                "Raaz 3",
                                "Raaz 3D",
                                "152M views",
                                "1 year ago",
                                "2:12:06",
                                "https://m.media-amazon.com/images/S/pv-target-images/78127490fdc2d9ba76361584d915587983a5664a9d6a0fc987e892b15df1d602.jpg",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_11"));

                // =====================================================
                // VIDEO 12
                // =====================================================

                videos.add(new Video(
                                12,
                                "Raaz Reboot",
                                "Raaz Reboot",
                                "50M views",
                                "6 months ago",
                                "2:06:18",
                                "https://a10.gaanacdn.com/gn_img/albums/10q3ZR1352/0q3ZA9A6K5/size_l.jpg",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_12"));

                // =====================================================
                // VIDEO 13
                // =====================================================

                videos.add(new Video(
                                13,
                                "Ek thi Daayan",
                                "Ek thi Daayan",
                                "118M views",
                                "12 years ago",
                                "2:11:06",
                                "https://th.bing.com/th/id/OIP.6jYyGT7PLez-jDAN4IFkQgHaHa?w=161&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_13"));

                // =====================================================
                // VIDEO 14
                // =====================================================

                videos.add(new Video(
                                14,
                                "Crook",
                                "Crook",
                                "10M views",
                                "11 Months ago",
                                "1:59:45",
                                "https://th.bing.com/th/id/OIP.T4B8ybFe3zhahGm_lz9s_QHaKn?w=208&h=299&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_14"));

                videos.add(new Video(
                                15,
                                "Gangster",
                                "Gangster",
                                "120M views",
                                "1 Year ago",
                                "1:57:45",
                                "https://th.bing.com/th/id/OIP.xUWDId8pJ4Ca5tuy8h1VTQHaKo?w=132&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_15"));
        }

        // =====================================================
        // GET ALL VIDEOS
        // =====================================================

        public List<Video> getAllVideos() {
                return videos;
        }

        // =====================================================
        // SEARCH VIDEOS
        // =====================================================
        // SEARCH VIDEOS
        // =====================================================

        public List<Video> searchVideos(String keyword) {

                return searchVideos(keyword, null);
        }

        public List<Video> searchVideos(String keyword, String category) {

                if (keyword == null || keyword.trim().isEmpty()) {
                        return videos;
                }

                String searchKeyword = keyword.toLowerCase().trim();

                return videos.stream()
                                .filter(video -> (video.getTitle() != null &&
                                                video.getTitle().toLowerCase().contains(searchKeyword))
                                                ||
                                                (video.getChannel() != null &&
                                                                video.getChannel().toLowerCase()
                                                                                .contains(searchKeyword)))
                                .filter(video -> category == null
                                                || category.isEmpty()
                                                || video.getCategory().equalsIgnoreCase(category))
                                .collect(Collectors.toList());
        }
        // =====================================================
        // LIKE VIDEO
        // =====================================================

        public Video likeVideo(int id) {

                for (Video video : videos) {

                        if (video.getId() == id) {

                                video.setLiked(true);

                                return video;
                        }
                }

                return null;
        }

        // =====================================================
        // UNLIKE VIDEO
        // =====================================================

        public Video unlikeVideo(int id) {

                for (Video video : videos) {

                        if (video.getId() == id) {

                                video.setLiked(false);

                                return video;
                        }
                }

                return null;
        }

        // =====================================================
        // GET LIKED VIDEOS
        // =====================================================

        public List<Video> getLikedVideos() {

                return videos.stream()
                                .filter(Video::isLiked)
                                .collect(Collectors.toList());
        }
}