import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import InvoiceTable from "./InvoiceTable";
import InvoiceFilter from "./InvoiceFilter";
import { Link } from "react-router-dom";

const ITEMS_PER_PAGE = 5;

const InvoiceIndex = () => {
    const [invoices, setInvoices] = useState([]);
    const [sellerListSatte, setSellerList] = useState([]);
    const [buyerListState, setBuyerList] = useState([]);
    const [productListState, setProductList] = useState([]);
    const [page, setPage] = useState(0);
        const [totalPages, setTotalPages] = useState(1);
        const [totalElements, setTotalElements] = useState(0);
    const [filterState, setFilter] = useState({
        sellerID: undefined,
        buyerID: undefined,
        product: undefined,
        minPrice: undefined,
        maxPrice: undefined,
        limit: undefined,
    });

    const deleteInvoice = async (id) => {
        try{
            await apiDelete("/api/invoices/" + id);
            const data = await apiGet("/api/invoices", { page, size: ITEMS_PER_PAGE });
            setInvoices(data.content);
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements);
        }catch(error) {
            console.log(error.message);
            alert(error.message);
        }
        setInvoices(invoices.filter((item) => item._id !== id));
    };
    useEffect(() => {
        apiGet("/api/invoices", { page, size: 1000})
            .then((data) => {
            setInvoices(data.content);
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements); 
        });
        apiGet("/api/persons").then((data) =>{
            console.log("Persons:", data);
            setSellerList(data.content); 
            setBuyerList(data.content); 
        });
        
    }, [page]);

    const handleChange = (e) => {
        if(e.target.value === "false" || e.target.value === "true" || e.target.value === ''){
            setFilter(prevState => {
                return{...prevState, [e.target.name]: undefined}
            });
        }else{
            setFilter(prevState => {
                return {...prevState, [e.target.name]: e.target.value}
            });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const params = filterState;
        const data = await apiGet("/api/invoices", params);
        setInvoices(data.content);
    }
    return(
        <div className="container">
            <div className="d-flex justify-content-center">
                <div className="btn btn-info mt-3">
                <Link to={"/invoices/statistics"} className="nav-link">
                Statistika faktur
                </Link>
            </div>
            </div>
            <h1>Seznam faktur</h1>
            <hr />
            <InvoiceFilter
             handleChange={handleChange}
             handleSubmit={handleSubmit}
             sellerList = {sellerListSatte}
             buyerList = {buyerListState}
             product= {productListState}
             filter={filterState}
             confirm="Filtrovat faktury"
            />
            <hr />
            <InvoiceTable
            deleteInvoice={deleteInvoice}
            items={invoices}
            totalElements={totalElements}
            totalPages={totalPages}
            setPage={setPage}
            page={page}
            label="Počet faktur:"
            />
        </div>
    );
};
export default InvoiceIndex;