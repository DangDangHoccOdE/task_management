import React from 'react';
import { Sidebar } from '../Sidebar/Sidebar';
import { TaskList } from '../TaskList/TaskList';
import './Home.css';

export const Home = () => {
    return (
        <div className="home-container">
            <div className="sidebar-container">
                <Sidebar />
            </div>
            <div className="right-side-part">
                <TaskList />
            </div>
        </div>
    );
};
