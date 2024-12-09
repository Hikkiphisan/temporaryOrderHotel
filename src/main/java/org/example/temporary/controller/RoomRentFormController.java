package org.example.temporary.controller;



import org.example.temporary.dao.RoomBookingDAO;
import org.example.temporary.dao.RoomBookingDaoImpl;
import org.example.temporary.model.RoomBooking;
import org.example.temporary.service.RoomBookingService;
import org.example.temporary.service.RoomBookingServiceImpl;

import java.sql.Timestamp;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "RoomRentFormController", urlPatterns = "/rentRoomForm_Temporary")
public class RoomRentFormController extends HttpServlet {
    private RoomBookingDAO roomrentBookingDAO;

    private RoomBookingService roomrentBookingService;
    private RoomBookingService cancelroomrentBookingService;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Tạo kết nối tới cơ sở dữ liệu
            String url = "jdbc:mysql://localhost:3306/fake_hotell";
            String username = "root";
            String password = "hikkiroku";


            Connection conn = DriverManager.getConnection(url, username, password);
            roomrentBookingDAO = new RoomBookingDaoImpl(conn);
            roomrentBookingService = new RoomBookingServiceImpl(roomrentBookingDAO);


        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Lỗi kết nối csdl hoặc service", e);
        }
    }

    // Xử lý yêu cầu GET: Lấy trên CSDL về để truyền vào view jsp
    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        try {
//            showRentRoomForm(request,response);
//        } catch (ServletException er) {
//            er.printStackTrace();
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("hien thi form dat phong".equals(action)) {
            try {
                showRentRoomForm(request, response);
            } catch (ServletException er) {
                er.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("hien thi form huy phong".equals(action)) {
            try {

                showCancelRentRoom(request, response);
            } catch (ServletException er) {
                er.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    // Xử lý yêu cầu POST: Cập nhật thông tin đặt phòng
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");


        String action = request.getParameter("action");



        if ("dat phong".equals(action)) {
            try {
                String startTimeStr = request.getParameter("startTime");
                String endTimeStr = request.getParameter("endTime");

                // Thay thế 'T' bằng khoảng trắng và thêm giây ":00" vào cuối
                startTimeStr = startTimeStr.replace('T', ' ') + ":00";
                endTimeStr = endTimeStr.replace('T', ' ') + ":00";


                int bookingId = Integer.parseInt(request.getParameter("bookingId"));

                String customerName = request.getParameter("customerName");

                Timestamp startTime = Timestamp.valueOf(startTimeStr);
                Timestamp endTime = Timestamp.valueOf(endTimeStr);




                // Gọi service để cập nhật thông tin đặt phòng
                boolean isSuccess = roomrentBookingService.updateRoom_DatPhong(bookingId, customerName, startTime, endTime);


                if (isSuccess) {

                    request.setAttribute("successMessage", "Đặt phòng thành công!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("RoomBooking/thongbaodatphongthanhcong.jsp");
                    dispatcher.forward(request, response);


                    //                    response.sendRedirect("/main_roombooking"); // Chuyển đến trang chính sau thành công
                } else {
                    request.setAttribute("errorMessage", "Không thể cập nhật thông tin đặt phòng.");   //cai dat sau
                    request.getRequestDispatcher("RoomBooking/rentRoomForm.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("/error_pageloiexception");
            }
        } else if ("huy phong".equals(action)) {


                try {
                    // Lấy thông tin cần thiết từ request
                    int bookingId = Integer.parseInt(request.getParameter("bookingId"));

                    String customerName = request.getParameter("customerName");

//                    Timestamp startTime = Timestamp.valueOf(startTimeStr);
//                    Timestamp endTime = Timestamp.valueOf(endTimeStr);


                    // Gọi service để thực hiện hủy phòng
                    boolean isCancelled = roomrentBookingService.updateRoom_HuyPhong_rutgon(bookingId);

                    if (isCancelled) {
                        // Thành công: Thông báo và chuyển hướng tới trang thành công
                        request.setAttribute("successMessage", "Hủy phòng thành công!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("RoomBooking/thongbaohuyphongthanhcong.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        // Lỗi trong quá trình hủy
                        request.setAttribute("errorMessage", "Không thể hủy phòng. Vui lòng thử lại sau.");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("RoomBooking/main_roombooking.jsp");
                        dispatcher.forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Nếu bookingId không hợp lệ
                    request.setAttribute("errorMessage", "ID đặt phòng không hợp lệ.");
                    request.getRequestDispatcher("RoomBooking/main_roombooking.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Bắt lỗi chung và chuyển đến trang lỗi
                    response.sendRedirect("/error_pageloiexception");
                }
        }  else if ("xuat doanh thu len bang income".equals(action)) {

            try {
                double total_revenue = Double.parseDouble(request.getParameter("total_revenue"));
                Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime"));
                Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime"));


//                    Timestamp startTime = Timestamp.valueOf(startTimeStr);
//                    Timestamp endTime = Timestamp.valueOf(endTimeStr);


                // Gọi service để thực hiện update len bang income
                boolean isCancelled = roomrentBookingService.updateTableRevenue_DatPhong(total_revenue,startTime,endTime);

                if (isCancelled) {
                    // Thành công: Thông báo và chuyển hướng tới trang thành công
                    request.setAttribute("successMessage", "Cap nhat doanh thu thanh cong!");
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("RoomBooking/incomeStatistics.jsp"); //chuyen huong tu bai cua Tung
                    RequestDispatcher dispatcher = request.getRequestDispatcher("RoomBooking/main_roombooking.jsp.jsp"); //chuyen huong tu bai cua Tung
                    dispatcher.forward(request, response);
                } else {
                    // Lỗi trong quá trình hủy
                    request.setAttribute("errorMessage", "Không thể cap nhat doanh thu. Vui lòng thử lại sau.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("RoomBooking/main_roombooking.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Nếu bookingId không hợp lệ
                request.setAttribute("errorMessage", ".");
                request.getRequestDispatcher("RoomBooking/main_roombookingloinumberformat.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                // Bắt lỗi chung và chuyển đến trang lỗi
                response.sendRedirect("/error_pageloiexception");
            }
        }
    }





    private void showRentRoomForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RoomBooking> roomsrentForm = roomrentBookingService.getAllRooms();
            request.setAttribute("roomsrentForm", roomsrentForm);
            request.getRequestDispatcher("RoomBooking/rentRoomForm_Temporary.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Không thể lấy danh sách phòng.");
            request.getRequestDispatcher("RoomBooking/rentRoomForm_Temporary.jsp").forward(request, response);
        }
    }


    private void showCancelRentRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<RoomBooking> cancelroomrentBookingService = roomrentBookingService.getAllRooms();
            request.setAttribute("cancelroomrentBooking", cancelroomrentBookingService);
            request.getRequestDispatcher("RoomBooking/cancelRentRoomForm.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Không thể huy phong.");
            request.getRequestDispatcher("RoomBooking/cancelRentRoomForm.jsp").forward(request, response);
        }
    }

}

