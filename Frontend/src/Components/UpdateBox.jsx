import { useState } from "react"
import "../Components/UpdateBox.css"
import COUNTRIES from "../Data/Countries"

function UpdateBox({ setOverlay, setBox, setChanged, flag }) {

    const [selected, setSelected] = useState(flag.allowedCountries)
    const [info, setInfo] = useState("")

    function addCountry(country) {
        console.log(selected)
        if (!country || selected.includes(country)) return
        setSelected((prev) => [...prev, country])
    }

    function removeCountry(country) {
        setSelected((prev) => prev.filter((c) => c !== country))
    }

    async function updateFlag(flag) {
        const flagName = document.getElementById("flagName").value
        const description = document.getElementById("description").value
        const rolloutPercent = document.getElementById("rolloutPercent").value
        const allowedCountries = selected

        if (!flagName || flagName.length === 0) {
            setInfo("Flag Name cannot be empty!")
            return
        }

        if (!rolloutPercent || isNaN(rolloutPercent) || rolloutPercent < 0 || rolloutPercent > 100) {
            setInfo("Rollout Percent must be between 0-100%")
            return
        }

        const payload = {
            flagName,
            description: description || null,
            enabled: rolloutPercent > 0,
            rolloutPercent,
            allowedCountries
        }

        await fetch(`/flags/update-flag/${flag.id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            credentials: "include",
            body: JSON.stringify(payload)
        })

        alert("Flag updated!")

        setBox(false)
        setOverlay(false)
        setChanged((prev) => !prev)
    }

    return (
        <>
            <div className="box">
                <div className="title">
                    Update Feature Flag
                </div>
                <form className="update-form">
                    <label className="update-form-label">
                        Flag Name
                    </label>
                    <input type="text" defaultValue={flag.flagName} className="update-form-input" id="flagName" placeholder="Name" onChange={() => { setInfo("") }}>
                    </input>

                    <label className="update-form-label">
                        Flag Description
                    </label>
                    <input type="text" defaultValue={flag.description} className="update-form-input" id="description" placeholder="Description">
                    </input>

                    <label className="update-form-label">
                        Rollout Percent
                    </label>
                    <input type="number" defaultValue={flag.rolloutPercent} className="update-form-input" id="rolloutPercent" min="0" max="100" placeholder="Rollout %" onChange={() => { setInfo("") }}>
                    </input>

                    <label className="update-form-label">
                        Allowed Countries
                    </label>
                    <div className="add-countries">
                        <select className="update-form-country-input" id="allowedCountries" defaultValue="" onChange={() => { setInfo("") }}>
                            <option value="" disabled hidden>
                                -- Select a country --
                            </option>
                            {COUNTRIES.map((c) => (
                                <option value={c}>
                                    {c}
                                </option>
                            ))}
                        </select>
                        <button type="button" className="add-button" onClick={() => addCountry(document.getElementById("allowedCountries").value)}>+</button>
                    </div>

                    <div className="selected-countries">
                        {selected.map((country) => (<div className="selected-country">
                            <div>{country}</div>
                            <button className="remove-country-button" type="button" onClick={() => removeCountry(country)}>X</button>
                        </div>))}
                    </div>

                    <p className="info">{info}</p>

                    <div className="buttons">
                        <button className="cancel-button" type="button" onClick={() => {
                            setSelected([])
                            setOverlay(false)
                            setBox(false)
                        }}>Cancel</button>
                        <button className="submit-button" type="button" onClick={() => updateFlag(flag)}>Submit</button>
                    </div>
                </form>
            </div>
        </>
    )
}

export default UpdateBox