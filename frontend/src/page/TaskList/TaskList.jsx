import React from 'react';
import { TaskCard } from "../Task/TaskCard/TaskCard";
import './TaskList.css';

export const TaskList = () => {
    return (
        <div className='task-list-container'>
            <div className='task-list'>
                {[1, 1, 1, 1].map((item, index) => <TaskCard key={index} />)}
            </div>
        </div>
    );
};
