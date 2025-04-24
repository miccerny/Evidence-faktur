import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import { apiGet } from "../utils/api";
import PersonDetail from "../persons/PersonDetail";
import dateStringFormatter from "../utils/dateStringFormatter";

const InvoiceDetail = () => {
    const {id} = useParams();
    const [invoice, setInvoice] = useState();

    useEffect(() => {
        apiGet("/api/invoices/" + id)
        .then(data => {
            setInvoice(data)
        })
        .catch(error => {
            console.error(error);
        });
    }, [id]);

    if(!invoice){
        return <p>Načítám...</p>
    }
    return(
        <>
        <div>
            <h1>Detail faktury</h1>
            <hr />

            <h3>Produkt: {invoice.product}</h3>

            <p><strong>Číslo faktury:</strong>
                <br/>
                {invoice.invoiceNumber}
            </p>

            <p><strong>Dodavatel:</strong>
                <br/>
                {invoice.seller.name}
                {/*
                 <p>
                    <strong>DIČ:</strong>
                    <br />
                    {person.seller.taxNumber}
                </p>
                <p>
                    <strong>Bankovní účet:</strong>
                    <br />
                    {person.seller.accountNumber}/{person.seller.bankCode} 
                </p>
                <p>
                    <strong>IBAN:</strong><br/>
                    ({person.seller.iban})
                </p>
                <p>
                    <strong>Tel.:</strong>
                    <br />
                    {person.seller.telephone}
                </p>
                <p>
                    <strong>Mail:</strong>
                    <br />
                    {person.seller.mail}
                </p>
                <p>
                    <strong>Sídlo:</strong>
                    <br />
                    {person.seller.street}, {person.seller.city} <br/>
                    {person.seller.zip}, {country}
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {person.seller.note}
                </p>
                
                 */}
            </p>

            <p><strong>Nakupující:</strong>
                <br/>
                {invoice.buyer.name}
                  {/*
                 <p>
                    <strong>DIČ:</strong>
                    <br />
                    {person.buyer.taxNumber}
                </p>
                <p>
                    <strong>Bankovní účet:</strong>
                    <br />
                    {person.buyer.accountNumber}/{person.buyer.bankCode} 
                </p>
                <p>
                    <strong>IBAN:</strong><br/>
                    ({person.buyer.iban})
                </p>
                <p>
                    <strong>Tel.:</strong>
                    <br />
                    {person.buyer.telephone}
                </p>
                <p>
                    <strong>Mail:</strong>
                    <br />
                    {person.buyer.mail}
                </p>
                <p>
                    <strong>Sídlo:</strong>
                    <br />
                    {person.buyer.street}, {person.buyer.city} <br/>
                    {person.buyer.zip}, {country}
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {person.buyer.note}
                </p>
                
                 */}

            </p>
            <p><strong>Datum vystavení faktury:</strong>
                <br/>
                {invoice.issued}
            </p> 

            <p><strong>Datum splatnosti faktury:</strong>
                <br/>
                {invoice.dueDate}
            </p> 
            <p><strong>Částka bez DPH</strong>
                <br/>
                {invoice.price}
            </p> 
            <p><strong>DPH</strong>
                <br/>
                {invoice.vat}
            </p> 
            <p><strong>Poznámky</strong>
                <br/>
                {invoice.note}
            </p> 



        </div>
        </>
    );




};
export default InvoiceDetail;
