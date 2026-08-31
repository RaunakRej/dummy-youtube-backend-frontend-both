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

                videos.add(new Video(
                                21,
                                "Tera Mera Rishta Purana (New Version)",
                                "Sony Entertainment",
                                "48M views",
                                "3 weeks ago",
                                "06:13",
                                "https://th.bing.com/th/id/OIP.NtQaw0sUzmQXiQq7AvAzvAHaEK?w=295&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                                "trending",
                                "https://www.youtube.com/embed/VIDEO_ID_21"));

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

                videos.add(new Video(
                                16,
                                "Dybbuk",
                                "Dybbuk",
                                "12M views",
                                "1 Year ago",
                                "1:42:33",
                                "https://m.media-amazon.com/images/M/MV5BMDA5YjQwM2QtNmZkZi00ZTk3LTg1M2ItMTg3NzU1ZmQyNzc3XkEyXkFqcGdeQXVyMTI1NDEyNTM5._V1_FMjpg_UX1000_.jpg",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_16"));

                videos.add(new Video(
                                17,
                                "Dil Diya Hai",
                                "Dil Diya Hai",
                                "13M views",
                                "4 Year ago",
                                "2:06:15",
                                "https://th.bing.com/th/id/OIP.O4S03TnKq9LvIe1u0NjnAQHaKe?w=132&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                                "movies",
                                "https://www.youtube.com/embed/VIDEO_ID_17"));

                videos.add(new Video(
                                18,
                                "Tu hi meri shab hein",
                                "Sony Entertainment",
                                "400M views",
                                "10 Year ago",
                                "05:36",
                                "data:image/webp;base64,UklGRmgyAABXRUJQVlA4IFwyAACQ0QCdASqUAeoAPp1EnEolo6KmqXcr0NATiWNu3V1++Ey8uu7vy6uN/IYI91Q6z7BNxdzof4AfAnygOvB9ADpjrT45p/wfys8//Nb739yualEy7n/2PrU/yP+149/Pr/V9Q78w/oX+m4VEAn55/eP+h/efVS+3/7vpf9qfYE/n39Z/5Pr1/wPGO9C9gP+lf4v/qf6T8pPl4/8P9r6P/qv9qfgJ/on96/8Prxf//3U/uX///dm/cs/XZUHN/EhvrM17zwUbndaqTPTVlmK9HyjP6qkdkfWlHYItycuxZVlnfPN28dBN5+QzI2h8Xdq4G3r8E/1BU9q955kF5PHpSw5vnIuMcBg3BIc9WmrczxWwEupRH4KseArfCZh6jFPApAHynxi9inMJYJMcStsff3d6Wy3dS2yhxITUdk0Y3zrLR/a0r34YTw9UPBc3xSSG7Odh1lqPcyuRYxbfyNWQhMUW8YSkt+H/a3Ga3ZKbWMaOlLGLoIRamb585DjToO5EM09vTJqYpiKXMjk2DMBZZZIeVzVME9ZqMVo2V20rnu9cMt8VlT9Foo7IAG1NDjpg/YSD9gxS4eK3G+Y+aN4dr4xlPbqsD50b4ZB3NJmYE2/Tz6C5RzCo6DGLqFoGnl+vbVByKMMtkawUkKuhsXFph3076BW5iuGBtqpFrElVypK/SYuMHXDt5vRoyrC6XVdpd8BmsbYoTsFl+YaJ6/0+ig0hEvPRxNn2dV15ZQQan8jryzkL9DSsrvwOx9RXNRdd89hjS5e16wI2bb14Z0RzpqUVmxkoI8Fy6Kg0dual7ZPwgZppqkCDmYa2R8xyuaf/7ND3hv6hvLK5eiSvq+tGRVkFKWDb2vKWEMgx1g4jPpbm2O8yM7q7Sjh6lY/C0jJmwQ6NqJ9HgNAKl32iVgG8GGMCEWSt8y/BvmFgToru6nwqyV/6hfLofFYGJd+CBs+VrjJwlKojQaR7iFmuif8Ld4w5ReuI6WKGeKxjeMzd3Iq8wg5JV2vsqhvdb+C+vzskJV+qJS9Gwvbf6HmjvD1obVojq+YdpdnH4YU5o1fBQrpLHOlAgywiWNvaKIExmbGVqs753ZeIF2RYFTi1QQeMsgGSnpuk9Z5acQTrHLJjV7wrRepmfVj1st1bxCoaGx9x33HUXeGsx/iovj/y4z1Dufm9Ni4X3ifC/VH5mMPxUWG8rqpEHSz6aHpt0s+11mM9YK/3bDhUXkHY5zAxRlkyo5+1vaKMe7k4jYvZC7EqA6GCA/NKashz7F/faAHdNRmahU/vFeizKnFW3BG2V1S6XNyNrTFAdgiaWuP1dCxIwIfHUh/JUJmcSEhx0enQ2Z/0MhjnXKl1sm9cH/A/4RNQ1GLPARZCgmw6xOQFs2sKCkmltZ/TJCYhbusRuzef5GtwxHwnYf8nDFMDaRWF6BHHkaDCHyp8BsvcfyduSANMsIk5b+KuFuBhg31OAm/gwiCkIlgev8tUZs2gFLQr7XjzVbK2TMYEdwgwZdCrFM78f6nGQQfD4U5Py2Gc68WyeehlGUMbazZRTgrIVTud49Ue4l2z85z+KIOc4RS76V4s/z7/4Efj+TIlY32WEayX7wgWZpn59R15nD0dI0NRUO1Vja2H4PwE1XRb1J0dYqvLRNjrm3Z8zYsXnj3NggrmX7a8UDcEb/H1XRTNsqwuv3/RpFOJJdE/UJ0sKKeRKNuksG1a41Q4wjWVXvMtH7iZwPrSZHrppDbV11JxFSfYWGHUqA+hBc7anldyiOwyuyYQFQQM9XI22gpOlQm2H/l/jN2DDrQ3+PxH17MU0tsM6f687tf8lfRxXKWvd6mVd7L3Coejz1xV+nn/g6dN0kLR3a1Nq7qDvx1kSqlZw0/wlPpE22V5mD5hTul35hH6v3ip5u7psRkycQKcYa3XGuqRvZARYaUgmeXCxJY3lim9boc/JgNh2/8TkBm/j/V6wdPTEcQ1Mhonf0++YBWRnDINh9IKOfhZ2OB0cKXyJxJDhWsetsegxNG7SyHJqc8ztwzd6OUoi+Lu7ohQlGDqPQr8Jd8WKAbA+1xwnye99SIUbAkdIAB8EXFP+YGlctESJtD1Ck7k2FRGmPl+ot9x6PWwYOZG8NSM2mXt8sv6hUCWvvIVadnU5wvVNLSt9/K3khlEhuCP0chTvVcw44Vj77h9fT5I3GHRjSCwbONaDr52y/fP/HuSbqfmfDZsZnEKS8OIVQsTUKhJnLPLAMUi7NfY3rM19jeszX2MwAD++5zAAAAAWDwpCl3pMzVQHnj4fC8HYB39z62BncY/ZOQPYdui/3TbI0r3MAY/ZiA58/StJNGboetbxkl/JCQ+lSw1enKDX5KpxT9vGivhHeNDhHAaNztxLJfLFgy4GajCrKY4QlYBClmwBzsOeHQU30lw7/LljSG407TpAnLRe+670zdeb3k8H7LRl7TlundETJGc2cCmI0NcSrHv0Wy0T+paw8Ur9vTyWmTdH7pPEo3X1epf/9437sma0uq3U02+K70lvDscJ0MAqrSjwia10Rl3gJiPAfLspRM9Lf2ZWRPPE0o+WJzYq3VhWdadOrH8LA4F3MTLRGnJyMMAHJVY/IIowosNkGRqPKSVriduMW6qkdHHHv/eVNTDEjAH2ak73IEUnD1EBxfgW6dLNmLUGLVBKdW/pyRSIFOUqaLarHbVtS8hdG936MPoTQHdHB8wPKd8qL9iMblCLtfjnUHaSEhirVC47BwBpjVgOUW/G54HT3swgZYHq0Ys5HjcjuNGJzWQxQgyk67M9PnnqngBJlhX9GKXM6tB7kjNGwv+gSv7gCRyU1O1GNX7UL94PV55Zsg+A9bK6UF9QZydxES3BNNdlWGNUXczSl/d1O6duLtcPsV/Ztr2J9QUbwdAe1SxnKM41kHPSyrE3zCkxjfk3Rk4Auxwg+gh65Y0wjTEtM65TykpYTcqsxUbVyEIZKDA3pInOuSgdvRigzHkEM21SLK0f3BYgImncVXnDs22Hn6gzFiA/jQIj/MhkAY4dDtd1ikERLVNapggnT+2Hm/ZRGyRXGJ0x9Wqic8U9cdbxSEABefASxNYwENbJVhPfayhlOBqTNp3mHnUNwfULEFVlB0Rjvmb3HCf0haxh5eADIdtBL+wEVjA0pdmlIMSIJ90pL8ujJ+/cQZyj6HKKIEnuqU/EueCnbg9riISTlMMgJBL5rCYZE/UFevGtvKbhVStU+eVAMPFvGtWum3cIkxDxXa3EfYsB1aUHp2X1k72lIyQWA+DJvOsl4Oou+djQb3zDoUG6QMkKBwBuOFsQ99UbEfVJwrqhizcjJ7BNYMhXKQR8f8nzTXMVAifQyDjEqcE2S0wpTl+rYKJpaloC15LVlbkQegTThKP+TnAfrKAXwwifDA6NzK6+LAxVmsk3LvpEkvisnq23MrzbjFKIJiEzjoB4yssG7NaH+aKlGtC6DbkdgvgAsqOuQNJhe1U6vkvtZ7Vv0UxUpW+S6muBGKtUdP+8v8qSLQss42QdB+suskCfp/qP3WZT9HgS8KWOjnKIzbCzXPp1Tpva6Pk4kW7p1SfzsEx13exi1WZE/3rxmtM/503LeSf/zOz/7M6kzxJGI5Z+wjZPxsF82IipR9CW/wEacIVPf0JH3LqLXWuZzYTPgjirs7O9t67AT0KWsKjmGkCABblGu+lAFJcGGOXeunmWtBW2/BqVPK8WqHzN0L6hD0sDm85ZkjTpSkFYhXbKfVicF/rXJmmozgYJtwpfukVriWxsgRTIw+DDxew5upt2wRuVbMvY/ZuQkcGnH94hIGR7+vhWNK/K1w4RA0QHxqyX6Rsg2f1lETeNtvc0GIuL9BdqFFzHddaAT+28JNGEgC5yikGlK4HLkaemDnUH7yOlHlAhca+0rJtQPapeyCvNnLnvDsqPvPrUIUKX8DCim5wFn2bGkBhMiCf57MNHKpLv9/lRMvuNGH8fFZ0h+7FFosw14YW/9NHvaJEn5GbtTZmgVIhrh6TYhafWq9h/xVvwtrZkU1bBIprfksFQWtYQdNDz1L4OssAiIcQb+adcMLtGzFCNPNTtxFVAwDWkbp6duZ3MzqO/ymmcgpPV2kjrxG0FvfmmdnsNc3VXRJxkn/f63tO16Aq3DmoIBi3YaH/dZyeCaOnAJrlnSqG38VMRvaY030aONKPXNvg4QGimiGvPPZqLL6+yvu7WJi8gCtbIU5vU6LnOfj02NZ2JT8tBFbqAH3b2HVN+3MaCRytlpfA9NRLBpEKOihLf1CXNdnY5YBIjsUfM0Zq/dBHibLpRkDjGf5z/wXuMXDUwiDn+RzBC2i9bhgPM/Cz66nmEA8q/FB81JECpeofWDqLAdMMTGGN+LkAjrq6T/Jd3UOiMTSjRvQ03gS742IGe19mAETzE5VIyaXGUotLwap+Acejuc735n1lm9ZSzf3uDHqeRHa6WgVS2Nj2x2NK+fMkyb29bt3UGL67vzyNQcgan6WebSbsZ5yG/F5vh7KCHUVuME9t4bR56ctV/uKyPA5kTdcnZmoC8paQq5uY8ttUvkKk2BnOPG6t4Z3vzDLhiouimJuhipbfb6xmNWoKB9Hl4RJUiTvbP8Ydp0LmTtxZkRsEW7NJUD9W7kRhynULEgdpSfZK1i851zLm+OHPwoS7w4Dl2cG0f8XrZArj3wcVBZhTX/NArA5gBRY9sCSF+hm5DJxJZat4Tx+yhgWM60eFDhC/j7rY6af+EbW4Zaj3oSFJNKBUrXa42OVP7WKgdVXZorz3HRnihAdv1Mxj/SxxZ/H/Ojg13xfw2xPieObMJZovHvK3axnJ8oQJYa1RJutofHhn+BF+8hWeALYtAXkeD1GHWByJOIfAAYM8X/emduK021Oe3d395RQmVALVrQJOjaVjNfZpj6sTrfLLG9zo38RcHarKbMYuFLuy4b5ldup0SBIAhbpT3I6c0+/u+spRZy2ikirhdC4yjpHe3ab4+rQq26QgszdooB9R0ZLFy5bh25/Ur7D+t6/sE385fFYrndFT+kcU76VErd+DyY6hH54d7P9sSJB/Qj0geKW81oBubskzXO1703V9s5mTuT2lv5iTm/i9pch9lXtXAxcpjW8jmg58qPkG2EYvzPHqOboq07M35uSbM7T2TJPUkdvBj99tH1QtpCnZ/MiumDEzzl45/UG5XArw+2mAjzcDcD5hyxWHIAudB594lO9l6BZ42a0W7j3ITZSBrrHoumDmTLfMuGpALhSBmoE80I2yMHO8WTUFkxZI2ns66x3n6cN3aA9klr9dN4PTZ/uE7iPI0WyTzUEoeF5UBJg30aEo+IuEkq7LHH+o0DT3BQUFAcZy6PwLv9u3f9Goc+xFjDJ+mDcxqGg4tn726OKHhA+2dqPk7rSbVQuZt2ai1m3g6k9e7s4UsVlbm38O8NO0bPoQVrbejRaZDSlvOhzVUJVAOkLQyihkJpLlUDqJQTr1tQuiVK41wKC+2IxGzKMeaW3ydjn1cL2jMOWG0bfV2wASJ9a5qJmFsFWHtzjz0yLTwbNJuOrn7lKARl0M/lOwS9f7kglZVWx9KJOKhIAaDFAJgo/RpoHg/+jtOo7Q1i2iZhsA5A7qtjdadq6yayp4aQYFUUAnaEFAzD4y1B9TPhd4I6Do2B3PlGpzN42KDRpC1lamUQ/a56wRw0Mdagt6abytiDYWNFFo/75dVCZZAye6tpE+eVe6cBrykzbFaCNmeY0vWdKBvnuvRVPGnOxLyDxwR1pYhj9VimsHhbL1yR4u52AWlabmU6EBWJ9P84Csa9dlyvKu+1MII5WtKDthUpv0otnpncmPwuBFF6WA6uM31nZyQwKFwK4g9t+FjUcpp7qosq9/hBnTiEYGJ4GaZU00Kdd/zEMPYOu0HbhLf46UE2MM/3PGbFQb9yvPKapgFzBZTLo0k1W/hs7rv3WO/ntufLc8wDBEyBFbC5VMwOWX3srpXYf8n48IliW59h0vO8E7Hn6GjMHTcGlXjnRYOcB/vJPM+XIZYJ4HTmwK2GvmBBww2xhIQstb0VMg0dQzbsMAcXeEpqi3/LCxBbUXHoCmFz+GgSqdccwQU0wV7u6FHUpuGDNiX2E0mZkgzPjtxPK15t+W66vCKaCyGtVty4VH9m8tw6ybxkDHtFdTcFbgfWGI44s95t7Lw/bpsa8NbszDGGUJSK8vkXKCxcwcMC4sOGlUadh+jJIpmirT3NjNta+KSaAhPf2RIv+QY893YYBXv6UW8g5odaHmas++sKuDVwsY2ma26hVx3A9CRZRBdthP/850+FF6XUH/TkCQQ/z7spsPU7ehhQYThpdrjAVoEE/lNMede5sWfrj+MyCCoPHb8R4A66+YHvZha329ADKqhnGfzljkB1AsTgkqiOtsCro11AHVWK6cYwnVXCSUXKnfzI3Iv/VsxXxGjwK/2NHiQCDZe9TjrVFkqxyH18W7oCCvVPk3o+PEhVBoab6vg7aJeDZzRfNfilrMpwpEqd4tvV195fHUfSFe2kAr+Yr8s2nS3DfPgOkTK4x0BoT6Lcdkfa/LYfN8/bQkFyUB/ooXilWWIHQw1ZuFc5gMiixXlJZc9/93O0w6+4Sw9hgtJtmeBHTndP90M+UxjLC4wu7c+NomzO6NToL7S/zzDv+N9m+G3DvORrTzAnNekqhetdoNsEdxpCGWICKan7HN6m2YUGJ/kYStCrvvTNQjAcjQMNGTnwVMG/i5aFjV9Jz3IRdD8CdAQGVRusRWLTdxv+nwraYaPFFb3baoPMQJZbr29zXUt5T8jHZ9w5GJF6j5xHgEeW5GxqyMtNhrnUJHmuWui8oAXMc1sRN1D1tFtX1Eg13YE3InOq4ao5qk3foS628VgBavX2gkSAXnGs7iRQGt4H32b2+SXEL+ukrCukjTsySwWIl+GcgRpIJ9tqcMn7DsT8zhdczcy4Qb8IhxnCiJbzbsfXeIcyJ9DXfMN3PG0VTxO4qyF4SDMkjYmKJUPpZWic7P2KwAGzhaAG2V+eWTDb0FRaTbEHUGKqX3sqWu8AK31ZXIdc4MkhTRBragaP3YW1NQO2daYrsrw1doQ7JAW/kcHD5gLcqPTpNcVIUcFIKkeLGpliepf3t4T8D1GyJ4d1A0rKSo6NmSpkKOmm6AJxLeEn+3BGWww4Ez6YcXTruzl7eii1WzJAViloBN6fsEthOH3TvXRy/kLCS1WfBLuyEA2a7f7duFI3yWagZbhlpTVrjsCuqTe4Fpp+ZbKgzO6Jzwc8x7YhRMEcuWXa03B+grYKFBjszjM1rgRLDdj2jaGz+AWWLNMOGKF8SNbm+itIWe/FKihjH0ysmPaj+K8YwDO4JLrcCjupzDoaCLP2jngB+5GPLfdYePIZgww+dhiXRdNHwAF0giCAA4No9wESZTiFgx+wQTxQohywq6je1La2zElbKiBENda94+T978JGHKmnsl0J4qccb0peGilw9d87PfPOiyPARxqzpal6CUAvomxvEaxtY0QqRdApE06+CjuFfq1g2/i5xum2attDRCmJdhRoidv3PgO/TC/GafoiZNX7dSDk9ADaNnw/49hiv5ClP93N2mfq2FAMCOOI4wqeiAbk3cSc1sFmFFDVeJPPK7zWO6g5PPWSmhnrcYSDAkSojPz4uzrbwwZkmRriYQE5G+TGd7F/oGo7ef5PdzcPplWtMqGDVzdnJTAieg7+oNUJyOQyBLprAaqHgB8Ha6mgLMSVaVDy59lNq3R+Jq+Hg3uykZFT/c93mjvt8+8InIk4LQqso/algvXw3vPDmJHbUW2tjv62IUH9khTsqU5Gg1fg2dBylwsJYPJNox7cPzyd17L8BiF0rCUTPFKx37SISYqwDyG1dvYmPO3NMlFEiiTxfHxf83qud9FdF22EL+eNsCQ0kG3n6I2A+vlfbI2WNYFrZjQuEOPC0Cb5HmmDenTgLEF/hSHqcuWD83Xo78cWDWtHsZd86mboBgqPKszTzXdDMT3L43m5Dd6kMRUd5xGlK2gYG/VCeeEyQhGLlePWrOq+mSW6cxShacXDRorLpokIVZ9f2iTWyJpcg8WlEx8eln8uNtVn3kx5Q7mPNG1Qmqay8ZyH8z1c2RGvek4TBl55pvWyVxRYOnzjFXQnsNKF+SxdSKhUtDsGlgsD1bdQNc7dIRr7NLxQDY3348/wm3nkzeeYEjLyVyWXGbYHJFGJ92f5o1VAZAFs5a2KYc+j/U6Gjv6uRfaV3vpWpqA38eReFrnhHURiuWSlAkrC9bmYUuOf49NGo9VabqrkmJb0QeQc4pZsDA9JuelvTs+OgMB5axkgAuaxAb7ULbMqPEEAE9dH8DFvHw4eLQyohNOHDZrJCJpV/SPVSzjRXQKO/t7M+EuW4PeXoU4C+9NFcv3wDA6Fy9FWDUw7FgnMVMlwPNpS0QackjGKGnmF/lsu6z/13WjhGp2i9XeH7kDh/g+PHmMmMv4rJgbBNvK56/kQyZ3QenpOoE7olLAEubJ5BNbxsfG9ZGqZe/1jbkgK+7azxV9BDkXfK756dbWle0GlA38Xg1+UU1llHMPB1jM/euUzXuGZoG3ctbE0rK40fUiW4diMxV8PbafhCvXnRXMsVh1Cs5/1w+cBTtASl4QGkgE5llMPNJVuEtJuXQYyvdRdjVNMf4Ezhrx/zeIE2NYRevkGQsjT+2Gl8GUjQRiWRsAZr7mHEs+YjJpPGCjgAUzdj4DoPt1DIy7BPS4bzDdnCzqHLzqlv/Y0Z0R/liQGk5P1WAOrA6PYAy6kmOIbFj/M3jFlJLkIkNdWhqnuQK57SOgj6nZdf3w0G2LCFiCxctv+WHnx2VIWdRTZ12l9F8lYXe1OHiE6NZy5/nZLHzdQgGhud7xj1RnZ//AI5e0UAWxBoAKFIsDC0cbtIFelDnW02q0azqY2I65vZxWk3IgoiSkTltaTINjUsWcIBpWYm8+hV8xBNTXWyKCK6GoVC67EO0M1KtWPn0GQ6O8xptRjtRW/wYTS5iM1syZAX4aaOmnVUqWXF9SFhOxTjlzzeakY13L2VBJ8za79PSVomdzyvMd2XqbKznZYZL6j+ungOA0gXJAYhZ7iSvKGnqFznwLUH9X6ivUtBJaX/5O7cwT8AaYHoO+duh3XEQq3y0lFBqL+wD0zhw++LxL3oj8yCr/uKfOjV6trtsBpKmSk2DPkL9lirlQp4caF2TbmJDQO+QU77f9ymrVrgXnPG4mMnm9GjalDpYJbPiOnZQGHxNSO4VF4cddmZv84kDFFBwTcX2Y4D2WbNWSJ3mcSUq0KioaDMMaFwayZ0Jxk2MECASKUlvABHZ/GgOzr7Jm5k6wIQ4c92iVLZLK9ad3G09YKEGRSITYyAWz0M1AIHFfK6P6lij2CVZoxEOIH5EWti2irB54I/GiHeoljmFf61EsX2yux70L49kzq0ERAA8uLo88zrOZqR8UNjhX7bkbnvCGQXWxqCoKgvZpFD3TrVBhmEf1/s0iAZvGXXYcNK6zNWBUkRbud0Vn6/UJ+UteXRuqxso5/mR7x3gUchsbvXBPEkou9Cdco7nu8u9R2UODHhdy99PEtaRE27fpoA2i6fQicXh+7i2+hTo0lPa/gPv4EB98bbAE7Ry9GSI+nZZDRJ+PMJwx7mOWIIkpaFux/O4AjEAg8RoKtk/GHJw6P1LBFsYuN9WIC0l5aGYZ4DwgXb0eTSdLIMVVk8a822jpuNFS2kBqGUp3uMxwaVQ5XvMB2e0/jmjAVM0k4FIfeuvrRj4j439Yi0X6j/ofBUboVO1ZeDhhrzVURNw5T/522tk7l74GOjaaer0QNRzPIjVOhY4FN6824+FQutrYIIsKU2FZixnsLODHL7VJacb5O0yBNynzOhwaFdzsHhGTBy5jpu9P6AXnlrNEYgzM+CM+XPD/vxUyJwLpTfuFH6a6sUwx+EJ1FGoH4oKoYMMN+5KkCzw5PSYNzOFrofwTMq6RJRR8hsSWj+Cns/0fcQvNAO8ooDdMATNeLQvP5oTvg+HifXyM5hOLSNqXVdZz6Z1/W9ouZ/cNUMcFDojepN9/zxPG2Pv+m1j/I412umkMEaM//B6lhUIhdjGM5UPL2TnVXboo60+u3Td7VqUptZ7gFdwJ38OKsOjS4cCkkY+OEkK8s90RTeGjclJJ3oQ8he7lW8PGYF4WmVQBCyBWMgq9sBju6LqZX/8kkbuQSN2QEpv8+Gy8/DyTiAZ7xED88kCT9OLStSssnT4uBF1IB5VAj6Mq0Hiw9qmfGsp/toJarHLIt9VhwfuFRA55poA9Yl76W0u/gGoPA17qW/MG5Cv6Sl6nfd6h7WBKa/s8pOC2QTiFBnU2wK4lclhKQebKyhsQxMr3bcTk7f64kIeZSSxkXJldgGG9TPuqPcGJaarCvY9pUr7cXuc6UBJI40+GgJ7p8Az60Oef3qr7bw0Nd6YD8vi6e+l1KYFij3EoRW/r4sHzfHElsHqUrDmXt7EUZNbJ7BHElB2nWgw+zOEI7v3A0JZiBPAeOxMSzGsqViU6s2tmQmXf84XWjW2YJdBcUEfqUwwqMw6Pp0JpQmrBVH+wCSIoEeqfW/nTzfexKempDgH0bZouWXA8l+eesMM/d3K1SNxY2gCqidPz508SeHofmPVSmomDebTeCHd5j2Un+SOuoJ0oFbLiPWK3nn9K83XvpHtLKdUQAq/hiNlQoO640lBzb0s9bFZiSS47oNQ+iLQhpBEIW3YZQlHwrv5ZlJc7I0tfUiGyoAUcZ47ORRKne/6sBjAoKMWyfbnsx7wSE7OLuf3CgYynLQODZMmKYllOPeIBtafCzfItO2emDhyMZmkV4hsmuH+1ryYltMxTnCrAVWTMc0/PhCtkuVSUH119g614d9hZbiLGKPOuwhW6hVhwCZ61B0v71TurMwNhCiSyK3bxTWrbUp6NViuGXAUcmKLRhedMteDQ59koSoPBfrMAutgbFATqXUDSfyQ5D3INsRn8Ciux3oAnFoF6NPblqdd98oHV7j1bxA2lWVednqHwC4VF8cpspNXh+WwcJUo8ehlTZa/GypEBtLsKJCDrHgxwH9gtaNW8YmvhDe2iLoXUTYH6x2Cgx1jbFY0n+kFYvxNSCvRuQGLf3RcpG7By4ifhbb8p81ARmN66I7ND/MtGK/WqvV7TDQpqDQh7WXvG4xk+W8CZydeUr5Sa+qkn/1gFJFhEZJUd1j+K1v/YSiOIstazJ5WghML8OMNLiUtNu1zVvs6V8Tr9RKM2uI7DBEBeiVvU4/qofPltZQdbOLOkdlApybAjr9a8h4KVm6eCf7VZZNpLHfgRI9yW3eKKBatEx3IQd6ZWHfUcETVY932VxoYOYcmhJAVheEjuGSDNSxqYSGmyCEl+MhC8fcBAZcdJRGu96W1EdSAGkL7cXI+fucLJFIT/hGSMLCYZm5nl5gyML4lFetUNIkF28SjuwoaqTHOKKvD93InciLj7Nhk8piHqV2KvqXzoYWkN7DwiqRWTKrQkK3tr2EZz3K/nutdiKgyXYoGWPvTgXAO2qEJLPVkIcJDjkP4F6lPx+vLm2Txrary4UyGiGRhqaApbkEO/VKrkO9d3LbOziXehpbgBxzJQK+42hSRQCkUmaqHls3rtULgxBGdu47ZYzkkML5SA34So9guYZHR+hGTtAH5o8t37V+vWJWiJPRonS6YWxVkp/REfaY0/NhD0mb3r672vQy4jY/PTVOW/dxmbvssGfH//x+laJbTdHQbzOYlanFKThqz8/sbigwHyDUqaVSKBh/zis8dLfPh5cw1XO/hy8lQ+NcoHMhPC8E4VzyVPDK3SVlTc+BW3G0nARHpDcrtodWRu2QI7Z3DJDieMWX973FUOTDNG2jj9UOF1UNZaPR6jjzQh7OmM6wLUDlyQhJJGvZurN38APNgWPsbN1G6GiQSOD5Ay1bylMsnvTjl3zbjnx7lQVap9YSLcpse5T8gojXa1Efec8cZlsa9jfA+4UgFwFbBQdw0VjXKTWfESr3e+fZu7JMDS+XsL/V4st5xFBoRuhtb1rkO6bJJ/e79hFIUeUOVWfVQINyYiM1c7H9jrzPexZ2jKp3QrO6X7xcj3b2qtikBj3+nDazknPVUPMquIbiee7c8O+r81P0OrsN6GHrRyDoH8CDEtGC8AFQ4tHBFK9N2GyuoxbARMHOPcF3P09ejoQsBJfo7FRTz4+NCkPluMVIO6EBOMPYMvcjdOfVdENhVQ4oxoxd19R4MWpbtI409kpclwnV3D/tq6hHLB5nNbyIQdFBoFeGmCe2env2SK2C1S+24+I+CaSukEWJbA+M5LpACMFztgNBA0uPm6YSX3uoPX4Ke5xIHqwdTZ6otz+fm1kEfL1sR7MBccbDWAOSPujh3ElzoQdaqa0c47TKT7evW1C4c6WCtFsou7EaqF88akAxHazhHchQ4Hlvv0mmhyoZFpMhBQAOdQ89ZA6Sj/TRDoxO5YLaV8MbFKIofxsOz7SqmhGS3qM6xpGQZP/IWsiZfFR8e2qxahUAA5BepA3lP7uoyosZEnV4mtVEIHPKj6kjtEDCYBjj4+8bOxBJFhDf61AM6J6q8Xc2YIsoWobaHeHckW4/eFiwazmUC1xBVEYyi8+5Q8OJzmbuwf59UpPlYcTPYB7JKGm9ytaGX7KvWTRzf8KwVe6pWqh7ZkVVUD74cjuWGXQPegf/mlgqxY5tN1wq5TjWh62saxFHw7iaKWcQIM6aM4M1+6r8lI896nXCafnuiGMmFOE44C4j2ctP15V2VaK04LeWDV6P7j+FcLJZZVBkrQMDHaMivxTIvk8wq0XxmhiA4Fj4ZdCLBwDkIEniIw9TYtlKjx6qRMSOh2euNrzuM4IsFHrSyaiEB70CKDV4jvnzcD8EhdI8y+9+vh/TQl0Nauak+/92MozuefPzRb7mgSFkTQrqBnfVPqgNa2m4lC2UYZUydyDZX7se8NeZeQuZJKATMn1pPE9qamlh31TfHCIfY8yqVETotB5oVO/ldeySd8G78wV7+eMM2hF5fO1SAznj0k+PX1Fpr39/je1+iEODWpdREcUV7D2PxtrLlwAOjBEDcxdV/RrCQ0MuZves2BIj4yoe4ZvyBbSVTMEYieFoikAOYAMeBfL4vvw2jjM/A0NYWExL2U+YPTIvqwttEAW0Xo4uE8dGZaUMqxFgL4HBEpr/RXSZy6ZqtTEugOACQtOgV9JSrD7XViwQBIq5iPuGXBK9HouBTtIB5fq750buYxExjmEXDPsQZW+2EPTGxyfz7toimp4NR9KBaUvOJZSDKBlLSoxhsb5PPEiYc64VMb+/aHrtgBW5BrzIl4p9WrXoCZ5SCyhXGCN+SbEKz8wpbig5XI3qmKaEDSLg6F3zKQYEt+Y0ezRyUtGnUZmg2cQVpWS1I45WG3t051xDq0A3f4xyOi0b/t9LcGjMMFf6ys3NU1XsSA6ca9kqOGcRDdH38+IWb9jmP3lBhotheLAM27sVQ72RW/IR4noQFn6HUIVUPnYYgm748QOB8worUkEYfxkA+LdGrsTBTzoD37wG75geNvVfqXEKDzx51ukLMUmRHzhL0BU/YN9SUogzO4Wrl56CT7DeTMWmykaCQmByKNcXwUk0AmMsAvziC1LW2qSMcyCnXIAq0oYnlTXe+KUYt86/ydpBFIajq9yBAr5g6TvBdSsxWqYA6skgEtleyWP08chu2AvYd67WpyQanrDPiaAD/9xKER3dR9CEp5lgTOkyhses5t8eDk1Uxd/4uBJDOqzQS/Ge/JdZAbSJCY2nG080A74WGUDFXndCt9NMcogkyVs6BAiGtp7PhldkNCJ1dI89iUdeUCv9F9H/vEN48APJsGqSsQlihtFSUn8r5FEcY8bnqOEmF0maq1mIVmwnauqntVqKrpEbWCHpVrw4BbOM90qvS4zg7vJm653VbG4vj0Ee4VGu7Ehlc3VeHMkmkLNc5j7qVDLl9LNBGhwtKq1p/MoayYa73/aoMxrTGwsX4i9B+Gxm5p02LE6cDhpLZ4cBYIS5EU54KXY7/NrdPVHFtKakaIxlcjoxW3DE8QLD6S1BXdQAmgGD+UZOPDqBwfcHpTWG3P3cpbq6XKcjidoakv24bVMPt/IkU+H/ZqntRDilOMT5mo6p9ZbBHij47FLPsWm29SnbZPf0prll7zx8EfHFOv7wg1pJlU4S8g3Hpb78QG8YAsZ1IxAklbxEVfFyLPxWUTfHtYSe0PAjIWkqnEkXjXTPl6C2JYkDwtIte/N2ygU5bfMCziLR8ezEpSYzW5ugC0/Xu6nQD6WViAmtI6jSJZF8WSsVRid2SVIBvCIymBpSvtpEacaQ/yxa41cSmX1+l9mN2H4AaysLwyb0qifTmGEBW+pW0Sk7TZGAb4oySvQi0RzTv0+qkq0C0X8SNymwVG1KaOnoLOFrLIPXYLz0yhIa8BW3tO17vulS5qGKMskxqcXcz7y+Xsh7dwZx8A5TWffLA6GBJwMLUODM7maN+vNKhBwU6dB9Vk1rB21j51yjJXXIDY27UHHWU5qjckeAkVCDyHFz1EepAVbcxPhLXXb7tY5D+UOPWFDo47WCl8+LnoDZOtUnVoGItdgoHTBk4ysRTw6mnx24MIxIEuhRZ/DD1pspC1keH81zyyOCUz1M6TofoQVaiKiJsXvGk0dGcsiWbZOs1RPzc7oarPHvgJtZJV0EkojF/wTJaDpj77Jdby/+5a/h8XWBW0VyBVgXkajPsz7g/obH8406k4PoJxzPdpp+C7zlaZuTi2mesHT8Exsl2W/KG3sltpraubxX5fvwQk7SSrEHPSr7RDffw7Et7frAK4uhNWvl64R3yDjIf0KufeXHXEV8QwtFDLMoqrdUBrOZWdPDCt/BrSxvXLNkAfnnugT5qksBCtTWGwYbn6SeGs3+uw20sNC4N6skrrdKzUppaoCpM699h0ifVA2SjcuZKFKjLZlC/J9FNh97YS8DA9H1vr4vPnZogEwI7ih7OYrkB/M6YHTm/K3PbtUYa4tnUwXk1jXUd3/M+kDT50sY4xOSZDWhDk1oZT/joiGheqp/6jMa4m3YbJa4mG4wHrSIos+wXeFAPzlA/O4bPeRLQI1mUOu6Fuu4e+M/sTTMi3Nmlrd+36MAJVWgTjh2176qLlUqNsOiLJLxjWbHW/ZEVN6dEXu+T963pXSWvv48CnQHGzGxO/IkXu7TiWI4UpiNwLDVzBu6cEhFs8BMA4PuNl1r3JAuReUSaQ9xoQUQFx1Oo0IKo14FVm0Mz3hG0brIB2PaSaLIXlHr+5wsYSoUd4xAkFad78oi4YT+g4V/LuUDGJ78XD8c4TVxJfYeA034Y7dpilXNpga9sXtmgYDHXZxklifG3L+CGCbQv49rF6Yml4+xdAy2W5jFjpco82G0Qq5lSG5mLId6Y1ZNxy+Hp9XpS6kMjILdTQAijeY2vulShK0edoixvJiDoNGD67wnpCikGwxWJqxMpZiTxtRofU3NX9L7hDMHJP0qf4JrckTuS9Lx/eZb3z/IwPVT0+zo88HxtrlPWBPUsu6NNgPH6URBrZ0ED/Yh3iNQngAD2pJrgsQ1qd8H591AqJxFKte+Swrb1jkxpFGkJoYjV+qcSHvSM9KrP5p+q5W1Q+s7X65F9eW0xVtQon38T0R09BWad/oxVA6YgfcFs3GWDax6SW9Lg3M0dcPQNVUFFbQ7CUsRAI6VAfDI2ubeT2JAkaaEJ5MkECpA62DbwnLdjp5WUduwONqRhs1sPY3/dSzs546qZwomRpGCcdxlLW/sLbl5XNkAjho5mibPZ+BnByWT9AH2C7RYB8gYyVHe2Yo0/PQz8us8vGjamsafK/ZgZdqo/adhEymPuwjdI3j5QMwFNDAMLQhtTLz+KunziHXpCCzd1RaTEcx2OSKQ2a+XLr7Ke67eU3lUQiWF0x2+NrnnmXzi/u9thcaXToRO0eX95glDzSH4isiCIGKKXSgSTEuES8Ml5mLGi1Z/T3P1y2WJIrP9+cKlIV3TTa+LbGpsXMpkpfSwOA9o7znxSl+Ryl2MJ8aSjdiO3goRI80ycilp4CSRjJf0HgE7fLurwHeUvqoc5BHW3kjt+Vh/aPtESpVR3ob6UbBils0DW3gLM2ukL6oErRY0rdAKOYSL3lDaXzHEcfhg6a+CAPQMHtCCXNHEEWzmVmfMyhQ4K4Ql8NWDDd5uuErdFa2fOQq7NsEb5+OJvKNaRpH7BOy1GIwhlvHjdXgsahgS6D9Ov01Oiim+Xwjlw9TTss6e+4QBVyY+PB1hg6TIORL16zLosGREfnWaK54RBkp+DOMSqxFa2QmbdI6x4toK3S2AYy7nZQRb5bLOU3Xn5szs5hAanim14jKN8nXs08cEbdhM4YPLRqiY0g2zOKQ1Kualy7nd4MNjRFOPQI3s4YIpxSofah8vsrrFf5za1H0rQ2mkrXmRlPFmpACBSQIzLp09Vt3Vxl4K7K0JfLIxadwQPSf4WFov7Jr1oCIIRPatBojfiFEk7UfA0EB6Z0nq9FGKwuIRIwhQuCJA12memvlH65xBkzseYyUCj2TfDsHyacBo63dLwYPSvTwS7uYn8x0uldpLeeAu8RROSncUkfQVycQeEgEjB4xwCX5sgATYn5Kuy+G7GFTxaAPELxanKQCOrwq36KyD8t41rrQm3RtlgqAIZ9F94saqIR4GfHjV4XEBLZYO4e6ENiu+3GfReKNiK3WBKIwP+v4AUVwhlovi6xymLhQ5D+yBvBkkRn+t1AupSsfoDMLjYVvPT9ZuAuUlOD9Y+e1b9hSRARGAw+zdKPW3PILKCGP5nmCXATIV1v/UAJdzQoJy91R8ytyxNyW5FAjhkvU7rzdfphw/3nJKLqr8dZ3pQI9YVdydl3bOM2tVKb/yACfl4vBzyZ+hfvoA1IHneUDspm1A5FVjSJOvBQwh6PXCkGKHeze154G+qUkyg8ELpn1JQFP5eQBwzmFaEFZWe42y3b8PVUgVyQA8kRdOvUVC74D2AF5VdCZd7RAJjbmM231y+Gg6ma/ZJUaNOtbrXo3bu3KgcXqz459YJI01s908QkSqQorXT/N4Cg1tr6q1hK8jGcXc94KSduqa8mwV/jZT7bj/7KOBA6PP93BGJ6JB5cgsQx1W2C3lu+H8IN5NMvMF1PfdtP4npW0bscAANu1XMLKBYiGq6WMVQhF1o2+ycYUNlgMUsrutuDPF2MZcAM9FYESB4uLEABqnuim3ysAA7J7AAAAAA",
                                "music",
                                "https://www.youtube.com/embed/VIDEO_ID_18"));

                videos.add(new Video(
                                19,
                                "Maahi",
                                "Sony Music India",
                                "265M views",
                                "12 Year ago",
                                "05:03",
                                "data:image/webp;base64,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",
                                "music",
                                "https://www.youtube.com/embed/VIDEO_ID_19"));

                videos.add(new Video(
                                20,
                                "Trance of Omi - OG",
                                "Sony Music South",
                                "21M views",
                                "8 Months ago",
                                "02:45",
                                "https://tse1.mm.bing.net/th?&id=OVP.TzRIXYe_jDgGF7MiMDCHaAEsDh&w=356&h=200&c=7&pid=1.7&rs=1",
                                "music",
                                "https://www.youtube.com/embed/VIDEO_ID_20"));

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

        // =====================================================
        // GET VIDEO BY ID
        // =====================================================

        public Video getVideoById(int id) {

                for (Video video : videos) {

                        if (video.getId() == id) {
                                return video;
                        }
                }

                return null;
        }
}
