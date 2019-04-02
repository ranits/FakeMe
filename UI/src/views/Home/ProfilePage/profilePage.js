import React from 'react'
import Button from "@material-ui/core/Button";
import Trump from "../../../images/image.jpg";

import "react-toggle/style.css"
import './ProfilePage.css'

const nameCapitalized = name =>name.charAt(0).toUpperCase() + name.slice(1)

const ProfilePage = (props) => {
    const {closeModal, profile} = props
    return (
        <div className='profile-page-wrapper'>
            <img src={Trump} alt=''/>
            <div className='fields-wrapper'>
                {Object.keys(profile).map((field, index) => (
                    <div key={`$field-${field}-${index}`} className='field-wrapper'>
                        <span> {`${nameCapitalized(field)}:`}</span>
                        <span> {profile[field]}</span>
                    </div>
                    )
                )}
            </div>
            <div className='cancel-submit-btn'>
                {/*<Button variant="contained" color="primary">*/}
                {/*    Save*/}
                {/*</Button>*/}
                <Button variant="contained" color="secondary" onClick={closeModal}>
                    Close
                </Button>
            </div>
        </div>
    )
}
export default ProfilePage