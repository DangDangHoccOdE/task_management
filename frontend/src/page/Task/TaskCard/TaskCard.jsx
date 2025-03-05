import React from 'react';
import './TaskCard.css';

export const TaskCard = () => {
    return (
        <div>
            <div className='task-card'>
                <div className='task-card-content'>
                    <div>
                        <img className='task-image' src="/img/logo.png" alt=""/>
                    </div>

                    <div className='task-info'>
                        <div>
                            <h1 className='task-title'>BookStore</h1>
                            <p className='task-description'>Use latest framework and technology to make this website</p>
                        </div>

                        <div className='tech-stack-container'>
                            {[1,1,1,1].map((item, index) =>
                                <span key={index} className='tech-stack'>Angular</span>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
