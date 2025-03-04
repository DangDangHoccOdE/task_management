import { Avatar } from '@mui/material';
import React from 'react';
import "./Navbar.css";

export const Navbar = () => {
    return (
        <div className='navbar z-10 sticky left-0 right-0 top-0 flex justify-between items-center px-5 lg:px-10'>
            <p className='font-bold text-lg'>Tech Task Manager</p>

            {/* Đảm bảo p và Avatar nằm chung một dòng */}
            <div className='flex items-center gap-3'>
                <p className='text-white font-medium'>Dangbaby</p>
                <Avatar sx={{ backgroundColor: '#c24dd0' }} className='bg-[#c24dd0]'>C</Avatar>
            </div>
        </div>
    );
}
