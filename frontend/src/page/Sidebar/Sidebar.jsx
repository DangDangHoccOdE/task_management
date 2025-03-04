import { Avatar } from "@mui/material";
import React, { useState } from "react";
import "./Sidebar.css";

const menu = [
  { name: "HOME", value: "HOME", role: ["ROLE_ADMIN", "ROLE_CUSTOMER"] },
  { name: "DONE", value: "DONE", role: ["ROLE_ADMIN", "ROLE_CUSTOMER"] },
  { name: "ASSIGNED", value: "ASSIGNED", role: ["ROLE_ADMIN"] },
  { name: "NOT ASSIGNED", value: "PENDING", role: ["ROLE_ADMIN"] },
  { name: "Create New Task", value: "", role: ["ROLE_ADMIN"] },
  { name: "Notification", value: "NOTIFICATION", role: ["ROLE_CUSTOMER"] },
];

const role = "ROLE_ADMIN";

export const Sidebar = () => {
  const [active, setActive] = useState("HOME");

  return (
    <div className="sidebar">
      <div className="avatar-container">
        <Avatar className="avatar" src="https://your-image-url.com" />
      </div>
      <div className="menu-list">
        {menu
          .filter((item) => item.role.includes(role))
          .map((item) => (
            <div
              key={item.value}
              className={`menuItem ${active === item.name ? "activeMenuItem" : ""}`}
              onClick={() => setActive(item.name)}
            >
              {item.name}
            </div>
          ))}
      </div>
    </div>
  );
};
