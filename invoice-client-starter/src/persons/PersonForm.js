/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {apiGet, apiPost, apiPut} from "../utils/api";
import InputField from "../components/InputField";
import InputCheck from "../components/InputCheck";
import FlashMessage from "../components/FlashMessage";
import Country from "./Country";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";


console.log("Načítám komponentu");

const PersonForm = () => {
    // React Router hook pro získání navigační funkce
    const {id} = useParams();
    // Výchozí hodnota osoby, pokud se jedná o vytvoření nové osoby
    const [person, setPerson] = useState({
        name: "",
        identificationNumber: "",
        taxNumber: "",
        accountNumber: "",
        bankCode: "",
        iban: "",
        telephone: "",
        mail: "",
        street: "",
        zip: "",
        city: "",
        country: Country.CZECHIA,
        note: ""
    });
    // Stavové proměnné pro sledování průběhu odesílání formuláře
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [submitting, setSubmitting] = useState(false);

    // Pokud existuje ID, načteme existující osobu z API při načtení komponenty
    useEffect(() => {
        if (id) {
            setIsLoading(true);
            apiGet("/api/persons/" + id)
            .then((data) => setPerson(data))
            .finally(() => setIsLoading(false));
        }
    }, [id]);

    // Zpracování odeslání formuláře – rozhoduje se podle toho, zda existuje ID
    const handleSubmit = (e) => {
        e.preventDefault();
        setSubmitting(true);

        (id ? apiPut("/api/persons/" + id, person) : apiPost("/api/persons", person))
            .then((data) => {
                setSent(true);
                setSuccess(true);
            })
            .catch((error) => {
                console.log(error.message);
                setError(error.message);
                setSent(true);
                setSuccess(false);
            })
            .finally(() => setSubmitting(false));

    };

    const sent = sentState;
    const success = successState;

    return (
        <div>
            {/* Flash message po odeslání formuláře */}
            {sent && (
                            <FlashMessage
                                theme={success ? "success" : ""}
                                text={success ? "Uložení osobnosti proběhlo úspěšně." : ""}
                            />
                        )}
            {/* Pokud se načítají data z backendu, zobrazí se informační zpráva*/}
            {isLoading && <p>Načítám data...</p>}
             {/* Nadpis formuláře - závisí na tom, jestli upravujeme nebo vytváříme osobnost */}
            <h1>{id ? "Upravit" : "Vytvořit"} osobnost</h1>
            <hr/>

            {/* Zobrazení chybové zprávy ze serveru */}
            {errorState ? (
                <div className="alert alert-danger">{errorState}</div>
            ) : null}

            
            <div className="mb-3">
            {/* Hlavní formulář */}
            <form onSubmit={handleSubmit}>
                {/* Jednotlivá vstupní pole */}
                <div className="mb-3">
                <InputField
                    required={true}
                    type="text"
                    name="personName"
                    min="3"
                    label="Jméno"
                    prompt="Jméno např. ITnetwork s.r.o."
                    value={person.name}
                    handleChange={(e) => {
                        setPerson({...person, name: e.target.value});
                    }}
                />
                </div>
                <div className="mb-3">
                <InputField
                    required={true}
                    type="text"
                    name="identificationNumber"
                    min="3"
                    label="IČO"
                    prompt="Zadejte IČO (8 místné číslo)"
                    value={person.identificationNumber}
                    handleChange={(e) => {
                        setPerson({...person, identificationNumber: e.target.value});
                    }}

                    disabled={id && person.identificationNumber !== ""}
                />
                </div>
                 {/* DIČ */}
                <InputField
                    required={true}
                    type="text"
                    name="taxNumber"
                    min="3"
                    label="DIČ"
                    prompt="Zadejte DIČ (8-10 číslic)"
                    value={person.taxNumber}
                    handleChange={(e) => {
                        setPerson({...person, taxNumber: e.target.value});
                    }}
                />
                {/* Číslo účtu a bankovní info */}
                <InputField
                    required={true}
                    type="text"
                    name="accountNumber"
                    min="3"
                    label="Číslo bankovního účtu"
                    prompt="Zadejte číslo bankovního účtu"
                    value={person.accountNumber}
                    handleChange={(e) => {
                        setPerson({...person, accountNumber: e.target.value});
                    }}
                />
                <InputField
                    required={true}
                    type="text"
                    name="bankCode"
                    min="3"
                    label="Kód banky"
                    prompt="Zadejte kód banky"
                    value={person.bankCode}
                    handleChange={(e) => {
                        setPerson({...person, bankCode: e.target.value});
                    }}
                />
                <InputField
                    required={true}
                    type="text"
                    name="IBAN"
                    min="3"
                    label="IBAN"
                    prompt="Zadejte IBAN"
                    value={person.iban}
                    handleChange={(e) => {
                        setPerson({...person, iban: e.target.value});
                    }}
                />
                {/* Telefon a mail */}
                <InputField
                    required={true}
                    type="text"
                    name="telephone"
                    min="3"
                    label="Telefon"
                    prompt="Zadejte Telefon"
                    value={person.telephone}
                    handleChange={(e) => {
                        setPerson({...person, telephone: e.target.value});
                    }}
                />
                <InputField
                    required={true}
                    type="text"
                    name="mail"
                    min="3"
                    label="Mail"
                    prompt="Zadejte mail"
                    value={person.mail}
                    handleChange={(e) => {
                        setPerson({...person, mail: e.target.value});
                    }}
                />
                {/* Adresa */}
                <InputField
                    required={true}
                    type="text"
                    name="street"
                    min="3"
                    label="Ulice"
                    prompt="Zadejte ulici"
                    value={person.street}
                    handleChange={(e) => {
                        setPerson({...person, street: e.target.value});
                    }}
                />
                <InputField
                    required={true}
                    type="text"
                    name="ZIP"
                    min="3"
                    label="PSČ"
                    prompt="Zadejte PSČ"
                    value={person.zip}
                    handleChange={(e) => {
                        setPerson({...person, zip: e.target.value});
                    }}
                />
                <InputField
                    required={true}
                    type="text"
                    name="city"
                    min="3"
                    label="Město"
                    prompt="Zadejte město"
                    value={person.city}
                    handleChange={(e) => {
                        setPerson({...person, city: e.target.value});
                    }}
                />
                {/* Poznámka (nepovinná) */}
                <InputField
                    required={false}
                    type="textarea"
                    name="note"
                    label="Poznámka"
                    value={person.note}
                    handleChange={(e) => {
                        setPerson({...person, note: e.target.value});
                    }}
                />
                {/* Země - výběr pomocí radio buttonů */}
                <h6>Země:</h6>
                <InputCheck
                    type="radio"
                    name="country"
                    label="Česká republika"
                    value={Country.CZECHIA}
                    handleChange={(e) => {
                        setPerson({...person, country: e.target.value});
                    }}
                    checked={Country.CZECHIA === person.country}
                />
                <InputCheck
                    type="radio"
                    name="country"
                    label="Slovensko"
                    value={Country.SLOVAKIA}
                    handleChange={(e) => {
                        setPerson({...person, country: e.target.value});
                    }}
                    checked={Country.SLOVAKIA === person.country}
                />
                {/* Tlačítko pro odeslání */}
                <input type="submit" className="btn btn-primary" value="Uložit" disabled={submitting}/>
            </form>
            </div>
        </div>
    );
};

export default PersonForm;
