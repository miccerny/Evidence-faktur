import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import { apiGet } from "../utils/api";
import Country from "./Country";

const PersonDetail = () => {
    const { id } = useParams();
    const [person, setPerson] = useState({});

    useEffect(() => {
        apiGet("/api/persons/" + id)
            .then(data => {
                setPerson(data);
            })
            .catch(error => {
                console.error(error);
            });

    }, [id]);
    const country = Country.CZECHIA === person.country ? "Česká republika" : "Slovensko";

    return (
        <>
            <div>
                <h1>Detail osoby</h1>
                <hr />

                    <div>
                        <Link to={`/purchasesAndSales/show/${person.identificationNumber}/sales`}>
                            <button className="btn btn-primary">Vystavené faktury</button>
                        </Link>
                        <Link to={`/purchasesAndSales/show/${person.identificationNumber}/purchases`}>
                            <button className="btn btn-primary ms-3">Přijaté faktury</button>
                        </Link>
                    </div>
                <hr />
                <h3>{person.name} ({person.identificationNumber})</h3>
                <p>
                    <strong>DIČ:</strong>
                    <br />
                    {person.taxNumber}
                </p>
                <p>
                    <strong>Bankovní účet:</strong>
                    <br />
                    {person.accountNumber}/{person.bankCode} 
                </p>
                <p>
                    <strong>IBAN:</strong><br/>
                    ({person.iban})
                </p>
                <p>
                    <strong>Tel.:</strong>
                    <br />
                    {person.telephone}
                </p>
                <p>
                    <strong>Mail:</strong>
                    <br />
                    {person.mail}
                </p>
                <p>
                    <strong>Sídlo:</strong>
                    <br />
                    {person.street}, {person.city} <br/>
                    {person.zip}, {country}
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {person.note}
                </p>
            </div>
        </>
    );
};

export default PersonDetail;
