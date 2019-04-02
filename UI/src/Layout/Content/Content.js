import React from 'react'
import { Switch, Route } from 'react-router-dom'
import routes from '../../Utils/Routes'

class Content extends React.Component {

    render() {
        return (
            <div className='content'>
                <Switch>
                    {
                        routes.map((route, index) => <Route key={index} {...route}/>)
                    }
                </Switch>
            </div>
        )
    }
}

export default Content