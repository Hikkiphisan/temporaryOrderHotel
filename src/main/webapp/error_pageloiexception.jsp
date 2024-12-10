<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Không tìm thấy trang</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background: linear-gradient(45deg, #1a1a1a, #2a2a2a);
            font-family: 'Arial', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            color: white;
        }

        .container {
            text-align: center;
            position: relative;
        }

        .error-code {
            font-size: 150px;
            font-weight: bold;
            position: relative;
            animation: glitch 1s linear infinite;
        }

        .error-code::before,
        .error-code::after {
            content: '404';
            position: absolute;
            left: 0;
            text-shadow: 2px 0 #ff0000;
            animation: glitch2 2s linear infinite;
        }

        .error-code::before {
            clip-path: polygon(0 0, 100% 0, 100% 33%, 0 33%);
            transform: translate(-2px, -2px);
            color: #00ff00;
        }

        .error-code::after {
            clip-path: polygon(0 67%, 100% 67%, 100% 100%, 0 100%);
            transform: translate(2px, 2px);
            color: #0000ff;
        }

        .error-message {
            font-size: 24px;
            margin: 20px 0;
            opacity: 0;
            animation: fadeIn 0.5s ease forwards 0.5s;
        }

        .home-button {
            padding: 15px 30px;
            font-size: 18px;
            background: none;
            border: 2px solid #fff;
            color: #fff;
            border-radius: 30px;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
            position: relative;
            overflow: hidden;
        }

        .home-button::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 50%;
            width: 0;
            height: 0;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
            transform: translate(-50%, -50%);
            transition: width 0.6s ease, height 0.6s ease;
        }

        .home-button:hover::before {
            width: 300px;
            height: 300px;
        }

        .circles {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: -1;
        }

        .circle {
            position: absolute;
            border-radius: 50%;
            background: rgba(255, 255, 255, 0.1);
            animation: float 8s infinite;
        }

        @keyframes glitch {
            2%, 64% {
                transform: translate(2px, 0) skew(0deg);
            }
            4%, 60% {
                transform: translate(-2px, 0) skew(0deg);
            }
            62% {
                transform: translate(0, 0) skew(5deg);
            }
        }

        @keyframes glitch2 {
            1% {
                transform: rotateX(10deg) skewX(90deg);
            }
            2% {
                transform: rotateX(0deg) skewX(0deg);
            }
        }

        @keyframes fadeIn {
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes float {
            0%, 100% {
                transform: translateY(0);
            }
            50% {
                transform: translateY(-20px);
            }
        }

        @media (max-width: 768px) {
            .error-code {
                font-size: 100px;
            }
            .error-message {
                font-size: 18px;
            }
        }
    </style>
</head>
<body>
<div class="circles">
    <script>
        // Tạo các vòng tròn nền động
        for(let i = 0; i < 10; i++) {
            const circle = document.createElement('div');
            circle.className = 'circle';
            circle.style.width = Math.random() * 100 + 50 + 'px';
            circle.style.height = circle.style.width;
            circle.style.left = Math.random() * 100 + '%';
            circle.style.top = Math.random() * 100 + '%';
            circle.style.animationDelay = Math.random() * 5 + 's';
            document.querySelector('.circles').appendChild(circle);
        }
    </script>
</div>

<div class="container">
    <div class="error-code">404</div>
    <div class="error-message">Xin lỗi, trang bạn đang tìm kiếm không tồn tại.</div>
    <a href="/" class="home-button">Trở về trang chủ</a>
</div>
</body>
</html>