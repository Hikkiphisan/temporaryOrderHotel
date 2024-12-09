package org.example.temporary.service;

//import controller.RoomBooking;
import org.example.temporary.dao.RoomBookingDAO;
import org.example.temporary.model.RoomBooking;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingDAO roomBookingDAO;



    public RoomBookingServiceImpl(RoomBookingDAO roomBookingDAO) {
        this.roomBookingDAO = roomBookingDAO;
    }  //RoomService không trực tiếp quản lý Connection, mà chỉ sử dụng DAO để thực hiện các tác vụ.




    @Override
    public List<RoomBooking> getAllRooms() {
        try {
            return roomBookingDAO.selectAllBooking();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi xảy ra
        }
    }



    @Override
    public RoomBooking getRoomById(int roomId) {
        try {
            return roomBookingDAO.selectBookingById(roomId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public RoomBooking getRoomByCode(String roomCode) {
        try {
            return roomBookingDAO.selectRoomByCode(roomCode);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean addRoom(RoomBooking room) {
        try {
            return roomBookingDAO.insertRoom(room);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRoom(RoomBooking room) {
        try {
            return roomBookingDAO.updateRoomtoSQL(room);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRoom(String roomtypeId, String customerName, String startTime, String endTime) {
        return false;
    }


    //phương thuc thử nghiem de sua du lieu tra ve tu rentroomform
    public boolean updateRoom_DatPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime) {
        try {
            // Cập nhật trạng thái phòng thành 'rented'
            boolean statusUpdated = roomBookingDAO.updateRoomStatustoSql_DatPhong(bookingId, customerName, startTime, endTime);

            return statusUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRoom_HuyPhong(int bookingId, String customerName, Timestamp startTime, Timestamp endTime) {
        return false;
    }


    public boolean updateRoom_HuyPhong_rutgon(int bookingId) {
        try {
            // Cập nhật trạng thái phòng thành 'rented'
            boolean statusUpdated = roomBookingDAO.updateRoomStatustoSql_HuyPhong_rutgon(bookingId);

            return statusUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateTableRevenue_DatPhong(double total_revenue, Timestamp startTime, Timestamp endTime) {
        try {
//           gửi trả dữ liệu ve sql
            boolean statusUpdated = roomBookingDAO.update_to_table_monthly_revenue_on_Sql_DatPhong(total_revenue, startTime, endTime);

            return statusUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean deleteRoom(int roomId) {
        try {
            return roomBookingDAO.deleteRoomtoSQL(roomId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}

