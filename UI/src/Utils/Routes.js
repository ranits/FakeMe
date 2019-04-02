import React from 'react'
import Home from '../views/Home/Home'

const ROUTES = [
    {
        path: '/',
        render: props => <Home {...props}/>
    }
]

export default ROUTES