-- 1. TẠO BẢNG NGƯỜI DÙNG (USERS)
CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,     -- Khóa chính, tự động tăng
                       username VARCHAR(50) NOT NULL UNIQUE,       -- Không được bỏ trống, Tên đăng nhập không được trùng
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Thời gian tạo mặc định là giờ hiện tại
);

-- 2. TẠO BẢNG PHÒNG CHAT (ROOMS)
CREATE TABLE rooms (
                       room_id INT AUTO_INCREMENT PRIMARY KEY,     -- Khóa chính
                       room_name VARCHAR(100) NOT NULL,            -- Tên phòng không được trống
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TẠO BẢNG THÀNH VIÊN PHÒNG (ROOM_MEMBERS) - Bảng trung gian xử lý quan hệ N-M
CREATE TABLE room_members (
                              room_id INT NOT NULL,
                              user_id INT NOT NULL,
                              joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Định nghĩa Khóa chính kết hợp (Composite Key): 1 người chỉ tham gia 1 phòng 1 lần
                              PRIMARY KEY (room_id, user_id),

    -- Khóa ngoại: Liên kết tới bảng rooms và users
                              FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 4. TẠO BẢNG TIN NHẮN (MESSAGES)
CREATE TABLE messages (
                          message_id INT AUTO_INCREMENT PRIMARY KEY,  -- Khóa chính
                          room_id INT NOT NULL,                       -- Tin nhắn này thuộc phòng nào?
                          user_id INT NOT NULL,                       -- Ai là người gửi?
                          content TEXT NOT NULL,                      -- Nội dung tin nhắn không được trống
                          sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- Tự động lấy giờ hệ thống khi gửi

    -- Khóa ngoại: Liên kết tới bảng rooms và users
                          FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);