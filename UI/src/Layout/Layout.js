import React from 'react'
import {HashRouter} from 'react-router-dom'
import Content from './Content/Content'

import './layout.css'

const Layout = props => (
    <HashRouter>
        <div className='wrapper'>
            <div className='content-wrapper'>
                <Content/>
            </div>
        </div>
    </HashRouter>
)

export default Layout
