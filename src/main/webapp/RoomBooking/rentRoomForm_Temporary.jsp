<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="utils.EncodingUtil" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cho thuê phòng</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            padding: 2rem 0;
        }

        .container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            box-shadow: 0 15px 30px rgba(0,0,0,0.1);
            padding: 2rem;
            max-width: 800px;
            margin: auto;
        }

        .page-title {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 2rem;
            font-weight: 700;
            position: relative;
            padding-bottom: 10px;
        }

        .page-title:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 3px;
            background: linear-gradient(90deg, #3498db, #2ecc71);
        }

        .room-image {
            width: 100%;
            height: 300px;
            object-fit: cover;
            border-radius: 10px;
            margin: 1rem 0;
            transition: transform 0.3s ease;
        }

        .room-image:hover {
            transform: scale(1.02);
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-label {
            font-weight: 600;
            color: #34495e;
        }

        .form-control {
            border-radius: 8px;
            border: 2px solid #eee;
            padding: 0.7rem;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: #3498db;
            box-shadow: 0 0 0 0.2rem rgba(52,152,219,0.25);
        }

        .room-info {
            background: #f8f9fa;
            padding: 1.5rem;
            border-radius: 10px;
            margin-bottom: 2rem;
        }

        .btn-submit {
            background: linear-gradient(45deg, #3498db, #2ecc71);
            border: none;
            color: white;
            padding: 12px 30px;
            border-radius: 50px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-submit:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(46,204,113,0.3);
        }

        .btn-back {
            background: #95a5a6;
            color: white;
            padding: 12px 30px;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-back:hover {
            background: #7f8c8d;
            color: white;
        }

        .price-tag {
            background: #2ecc71;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            display: inline-block;
            font-weight: 600;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .animate-fade-in {
            animation: fadeIn 0.5s ease-out forwards;
        }
    </style>
</head>
<body>
<div class="container animate-fade-in">
    <h1 class="page-title animate__animated animate__fadeInDown">
        <i class="fas fa-home"></i> Cho thuê phòng
    </h1>

    <form action="${pageContext.request.contextPath}/RoomBooking/RoomRentFormController"
          method="GET"
          id="rentRoomForm"
          class="needs-validation"
          novalidate>

        <div class="room-info animate__animated animate__fadeIn">
            <div class="row">
                <div class="col-md-6">
                    <img src="${param.roomImgLink}"
                         alt="Room Image"
                         class="room-image shadow"
                         onerror="this.src='https://via.placeholder.com/400x300?text=No+Image+Available'">
                </div>
                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Mã phòng:</label>
                        <div class="h5">${param.roomCode}</div>
                        <input type="hidden" name="roomCode" value="${param.roomCode}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mô tả phòng:</label>
                        <div class="h6">${EncodingUtil.fixEncoding(param.roomDescription)}</div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Vị trí:</label>
                        <div class="h6">
                            <i class="fas fa-map-marker-alt text-danger"></i>
                            ${EncodingUtil.fixEncoding(param.roomLocation)}
                        </div>
                    </div>

                    <div class="price-tag">
                        <i class="fas fa-tag"></i>
                        ${param.roomPrice} VNĐ/giờ
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="customerName" class="form-label">
                <i class="fas fa-user"></i> Tên khách hàng
            </label>
            <input type="text"
                   class="form-control"
                   id="customerName"
                   name="customerName"
                   required>
            <div class="invalid-feedback">
                Vui lòng nhập tên khách hàng
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="startTime" class="form-label">
                        <i class="fas fa-calendar-alt"></i> Ngày bắt đầu thuê
                    </label>
                    <input type="datetime-local"
                           class="form-control"
                           id="startTime"
                           name="startTime"
                           required>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="endTime" class="form-label">
                        <i class="fas fa-calendar-check"></i> Ngày kết thúc thuê
                    </label>
                    <input type="datetime-local"
                           class="form-control"
                           id="endTime"
                           name="endTime"
                           required>
                </div>
            </div>
        </div>

        <input type="hidden" id="roomStatus" name="roomStatus" value="Rented">

        <div class="d-flex justify-content-between mt-4">
            <a href="${pageContext.request.contextPath}/RoomBooking/roomList.jsp"
               class="btn btn-back">
                <i class="fas fa-arrow-left"></i> Quay lại
            </a>
            <button type="submit" class="btn btn-submit">
                <i class="fas fa-save"></i> Xác nhận đặt phòng
            </button>
        </div>
    </form>
</div>

<!-- Bootstrap Bundle với Popper -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<!-- SweetAlert2 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert2/11.4.8/sweetalert2.all.min.js"></script>

<script>
    // Form validation
    (function () {
        'use strict'
        const forms = document.querySelectorAll('.needs-validation')
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    })()

    // Tính toán thời gian thuê
    document.getElementById('endTime').addEventListener('change', function () {
        const startTime = new Date(document.getElementById('startTime').value);
        const endTime = new Date(this.value);
        const pricePerHour = parseFloat(document.getElementById('roomPrice').value);

        if (endTime > startTime) {
            const diffHours = Math.ceil((endTime - startTime) / (1000 * 60 * 60));
            const totalPrice = diffHours * pricePerHour;

            Swal.fire({
                title: 'Chi tiết đặt phòng',
                html: `
                        <div class="text-start">
                            <p><strong>Thời gian thuê:</strong> ${diffHours} giờ</p>
                            <p><strong>Tổng tiền:</strong> ${totalPrice.toLocaleString('vi-VN')} VNĐ</p>
                        </div>
                    `,
                icon: 'info',
                confirmButtonText: 'Xác nhận',
                confirmButtonColor: '#3498db'
            });
        } else {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Thời gian kết thúc phải sau thời gian bắt đầu',
                icon: 'error',
                confirmButtonColor: '#e74c3c'
            });
            this.value = '';
        }
    });

    // Animation khi scroll
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('animate__fadeIn');
            }
        });
    });

    document.querySelectorAll('.form-group').forEach((el) => observer.observe(el));
</script>
</body>
</html>