import React from 'react'
import CircularProgress from '@material-ui/core/CircularProgress';

import './loading.css'

const Loading = (props) => {
    const {status} = props
    return (
        <div className='loading-wrapper'>
            <span className='status'>{`${status || 'Processing'}...`}</span>
            <CircularProgress
                size={100}
            />
        </div>
    )

}

export default Loading