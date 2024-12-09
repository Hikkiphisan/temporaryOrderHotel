package org.example.temporary.dao;

import org.example.temporary.model.RoomBooking;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomBookingDaoImpl implements RoomBookingDAO {
    private Connection connection;

    public RoomBookingDaoImpl(Connection connection) {
        this.connection = connection;    // phương thức cần thiết để kết nối với cơ sở dữ liệu, cần phải có để thực hiện các câu truy vấn CSDL
    }

    @Override
    public List<RoomBooking> selectAllRooms() throws SQLException {
        return Collections.emptyList();
    }

    public List<RoomBooking> selectAllBooking() throws SQLException {   //lấy thông tin trên CSDL về để nhét vào arraylist rooms.
        String query = "SELECT * FROM bookingver3";
        List<RoomBooking> rooms = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                RoomBooking room = new RoomBooking(
                        rs.getInt("BookingId"),
                        rs.getString("CustomerName"),
                        rs.getInt("RoomId"),
                        rs.getString("RoomLocation"),
                        rs.getString("RoomDescription"),
                        rs.getString("RoomImgLink"),
                        rs.getDouble("RoomPrice"),
                        rs.getString("RoomStatus"),
                        rs.getTimestamp("BookingStartDate"),
                        rs.getTimestamp("BookingEndDate"),
                        rs.getDouble("BookingTotalPrice")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }

    @Override
    public RoomBooking selectRoomById(int roomId) throws SQLException {
        return null;
    }

    //ver 3
    public RoomBooking selectBookingById(int roomId) throws SQLException { // tìm roomID từ CSDL ở vị trí id thứ ? rồi lấy toàn bộ CSDL của dòng id ấy
        String query = "SELECT * FROM bookingver3 WHERE room_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, roomId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new RoomBooking(
                            rs.getInt("BookingId"),
                            rs.getString("CustomerName"),
                            rs.getInt("RoomId"),
                            rs.getString("RoomLocation"),
                            rs.getString("RoomDescription"),
                            rs.getString("RoomImgLink"),
                            rs.getDouble("RoomPrice"),
                            rs.getString("RoomStatus"),
                            rs.getTimestamp("BookingStartDate"),
                            rs.getTimestamp("BookingEndDate"),
                            rs.getDouble("BookingTotalPrice")
                    );
                }
            }
        }
        return null;   //Đây là else if, nếu không có dữ liệu sau khi đã (rs.next) thì nó sẽ trả về null.
    }




   //loai bo
//    public RoomBooking selectRoomById(int roomId) throws SQLException { // tìm roomID từ CSDL ở vị trí id thứ ? rồi lấy toàn bộ CSDL của dòng id ấy
//        String query = "SELECT * FROM room WHERE room_id = ?";
//        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setInt(1, roomId);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    return new RoomBooking(
//                            rs.getInt("RoomId"),
//                            rs.getInt("RoomTypeId"),
//                            rs.getInt("RoomOwner"),
//                            rs.getString("RoomCode"),
//                            rs.getString("RoomLocation"),
//                            rs.getString("RoomDescription"),
//                            rs.getString("RoomImgLink"),
//                            rs.getDouble("RoomPrice"),
//                            rs.getString("RoomStatus"),
//                            rs.getTimestamp("RoomCreateDate"),
//                            rs.getTimestamp("RoomUpdateDate")
//                    );
//                }
//            }
//        }
//        return null;   //Đây là else if, nếu không có dữ liệu sau khi đã (rs.next) thì nó sẽ trả về null.
//    }



//phuong thuc nay khong co roomcode roi
    public RoomBooking selectRoomByCode(String roomCode) throws SQLException {
        // Truy vấn SQL để tìm phòng theo mã phòng
        String query = "SELECT * FROM room WHERE room_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Gán giá trị tham số roomCode vào câu lệnh SQL
            pstmt.setString(1, roomCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Nếu tìm thấy kết quả, tạo và trả về đối tượng RoomBooking
                if (rs.next()) {
                    return new RoomBooking(
                            rs.getInt("BookingId"),
                            rs.getString("CustomerName"),
                            rs.getInt("RoomId"),
                            rs.getString("RoomLocation"),
                            rs.getString("RoomDescription"),
                            rs.getString("RoomImgLink"),
                            rs.getDouble("RoomPrice"),
                            rs.getString("RoomStatus"),
                            rs.getTimestamp("BookingStartDate"),
                            rs.getTimestamp("BoookingEndDate"),
                            rs.getDouble("BookingTotalPrice")
                    );

                }
            }
        }
        // Trả về null nếu không tìm thấy phòng
        return null;
    }




//phuong thuc nay khong can dung

    public boolean insertBooking(RoomBooking roomBooking, int userId, Timestamp bookingStartDate, Timestamp bookingEndDate, double bookingTotalPrice) throws SQLException {
        // Truyền dữ liệu đặt phòng từ người dùng vào cơ sở dữ liệu
        String query = "INSERT INTO Bookingver3 (CustomerName, RoomTypeId, UserId, RoomLocation, RoomDescription, RoomImgLink, RoomPrice, RoomStatus, BookingStartDate, BookingEndDate, BookingTotalPrice) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Đặt giá trị cho các tham số
            pstmt.setString(1, roomBooking.getCustomerName());
            pstmt.setInt(2, roomBooking.getRoomTypeId());
            pstmt.setInt(3, userId);
            pstmt.setString(4, roomBooking.getRoomLocation());
            pstmt.setString(5, roomBooking.getRoomDescription());
            pstmt.setString(6, roomBooking.getRoomImgLink());
            pstmt.setDouble(7, roomBooking.getRoomPrice());
            pstmt.setString(8, roomBooking.getRoomStatus());
            pstmt.setTimestamp(9, bookingStartDate);
            pstmt.setTimestamp(10, bookingEndDate);
            pstmt.setDouble(11, bookingTotalPrice);

            // Thực thi truy vấn
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean insertRoom(RoomBooking room) throws SQLException {
        return false;
    }

    //thu nghiem Phương thức này cập nhật trạng thái của phòng dựa trên mã phòng (roomCode).
    public boolean updateRoomStatustoSql_DatPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "UPDATE bookingver3 SET RoomStatus = 'rented', CustomerName = ?, BookingStartDate = ?, BookingEndDate = ? WHERE BookingId = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, customerName);
            pstmt.setTimestamp(2, startTime);   // Thời gian bắt đầu đặt phòng (do người dùng nhập)
            pstmt.setTimestamp(3, endTime);
            pstmt.setInt(4, bookingId);;
            int rowsAffected = pstmt.executeUpdate(); // Thực thi câu lệnh
            logRowsAffected(rowsAffected); // Log kết quả rowsAffected
            return rowsAffected > 0;
        }
    }



















    private void logRowsAffected(int rowsAffected) {
        if (rowsAffected > 0) {
            System.out.println("Rows affected: " + rowsAffected + ". Update successful.");
        } else if (rowsAffected == 0) {
            System.out.println("Rows affected: 0. No rows were updated.");
        } else {
            System.err.println("Unexpected value of rowsAffected: " + rowsAffected);
        }
    }




    public boolean updateRoomStatustoSql_HuyPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "UPDATE bookingver3 SET RoomStatus = 'rented', CustomerName = 'Hiện tại không có ai thuê', BookingStartDate = '1970-01-01 00:00:00', BookingEndDate = '1970-01-01 00:00:00' WHERE BookingId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        }
    }











    /*
    public boolean updateRoomStatustoSql_HuyPhong_rutgon(int bookingId) throws SQLException {
        String query = "UPDATE bookingver3 SET RoomStatus = 'rented', CustomerName = 'Hiện tại không có ai thuê', " +
                "BookingStartDate = '1970-01-01 00:00:00', BookingEndDate = '1970-01-01 00:00:00' WHERE BookingId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId); // Tham số bắt đầu từ 1
            return pstmt.executeUpdate() > 0;
        }
    }
*/

    public boolean updateRoomStatustoSql_HuyPhong_rutgon(int bookingId) throws SQLException {
        String query = "UPDATE bookingver3 SET RoomStatus = 'available', CustomerName = 'Hiện tại không có ai thuê', BookingStartDate = '2000-01-01 00:00:00', BookingEndDate = '2000-01-01 00:00:00' WHERE BookingId = ?";
        ;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Kiểm tra giá trị đầu vào
            if (bookingId <= 0) {
                throw new IllegalArgumentException("BookingId không hợp lệ: " + bookingId);
            }

            // Gán tham số vào PreparedStatement
            pstmt.setInt(1, bookingId);

            // Ghi log câu truy vấn (chỉ để debug, không dùng trong môi trường sản xuất)
            System.out.println("Executing query: " + pstmt);

            // Thực thi câu lệnh SQL
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Báo lỗi để lớp gọi xử lý
        }
    }






    @Override
    public boolean updateRoomStatustoSql(String roomCode, String status) throws SQLException {
        return false;
    }

    @Override
    public boolean insertBookingToSql(String roomCode, String customerName, String startTime, String endTime) throws SQLException {
        return false;
    }


    public boolean updateRoomtoSQL(RoomBooking room) throws SQLException { //sửa thông tin ở một ví trí nào đó trong CSDL
        String query = "UPDATE room SET BookingId = ?, Userid = ?, room_code = ?, room_location = ?, room_description = ?, room_img_link = ?, room_price = ?, room_status = ?, room_update_date = ? WHERE room_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, room.getRoomTypeId());
            pstmt.setInt(2, room.getRoomOwner());
            pstmt.setString(3, room.getRoomCode());
            pstmt.setString(4, room.getRoomLocation());
            pstmt.setString(5, room.getRoomDescription());
            pstmt.setString(6, room.getRoomImgLink());
            pstmt.setDouble(7, room.getRoomPrice());
            pstmt.setString(8, room.getRoomStatus());
            pstmt.setTimestamp(9, room.getRoomUpdateDate());
            pstmt.setInt(10, room.getRoomId());




            return pstmt.executeUpdate() > 0; //Phương thức trả về số lượng số nguyên đại diện cho số dòng bị thay đổi dữ liệu, trong trường hợp này nếu > 0 tức là đã có hơn 1 dòng bị thay đổi, nếu =0 tức là không có dòng nào bị thay đổi.
        }
    }





    //khong dung
    public boolean deleteRoomtoSQL(int bookingId) throws SQLException {
        String query = "DELETE FROM booking WHERE BookingId = ? AND Bookingtatus = 'available'";  //Chỉ các bản ghi có cột room_status có giá trị available mới được phép xóa.
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        }
    }















    //int month, int year, double total_revenue, Timestamp startTime, Timestamp endTime nhap tu view gui ve
    public boolean update_to_table_monthly_revenue_on_Sql_DatPhong(double total_revenue, Timestamp startTime, Timestamp endTime) throws SQLException {
        // Phân chia dữ liệu theo giờ tính toán trên các tháng nếu trải dài hai tháng khác nhau.
        double[] calculatedHours = calculateHoursByMonth(startTime, endTime);
        double revenueForMonth1 = calculatedHours[0] * total_revenue / (calculatedHours[0] + calculatedHours[1]);
        double revenueForMonth2 = calculatedHours[1] * total_revenue / (calculatedHours[0] + calculatedHours[1]);

        String query = "UPDATE monthly_revenue SET total_revenue = total_revenue + ? WHERE month = ? AND year = ?";
        String querySecondMonth = "UPDATE monthly_revenue SET total_revenue = total_revenue + ? WHERE month = ? AND year = ?";

        try (PreparedStatement pstmt1 = connection.prepareStatement(query);
             PreparedStatement pstmt2 = connection.prepareStatement(querySecondMonth)) {


            // Update tháng đầu tiên
            pstmt1.setDouble(1, revenueForMonth1);
            pstmt1.setInt(2, startTime.toLocalDateTime().getMonthValue());
            pstmt1.setInt(3, startTime.toLocalDateTime().getYear());

            // Update tháng thứ hai
            pstmt2.setDouble(1, revenueForMonth2);
            pstmt2.setInt(2, endTime.toLocalDateTime().getMonthValue());
            pstmt2.setInt(3, endTime.toLocalDateTime().getYear());

            int rowsAffected1 = pstmt1.executeUpdate();
            int rowsAffected2 = pstmt2.executeUpdate();

            logRowsAffected(rowsAffected1, rowsAffected2);

            return rowsAffected1 > 0 || rowsAffected2 > 0;
        }
    }




    public double[] calculateHoursByMonth(Timestamp startTime, Timestamp endTime) {
        LocalDateTime startLocal = startTime.toLocalDateTime();  //ding dang Time Stamp ve LocalDateTime
        LocalDateTime endLocal = endTime.toLocalDateTime();

        if (startLocal.getYear() == endLocal.getYear() && startLocal.getMonth() == endLocal.getMonth()) {
            long hours = Duration.between(startLocal, endLocal).toHours();
            return new double[]{hours, 0};
        }

        LocalDateTime endOfFirstMonth = LocalDateTime.of(
                startLocal.getYear(),
                startLocal.getMonth(),
                startLocal.getMonth().length(true),
                23, 59
        );

        double hoursInMonth1 = Duration.between(startLocal, endOfFirstMonth).toHours();
        LocalDateTime startOfSecondMonth = LocalDateTime.of(
                endLocal.getYear(),
                endLocal.getMonth(),
                1, 0, 0
        );

        double hoursInMonth2 = Duration.between(startOfSecondMonth, endLocal).toHours();

        return new double[]{hoursInMonth1, hoursInMonth2};
    }



    private void logRowsAffected(int rows1, int rows2) {
        System.out.println("Các hàng bị ảnh hưởng trong tháng đầu tiên: " + rows1);
        System.out.println("Các hàng bị ảnh hưởng trong tháng thu hai: " + rows2);
    }









}
