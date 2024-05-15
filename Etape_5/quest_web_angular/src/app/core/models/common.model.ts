export interface IAddress {
    id: string;
    street: string;
    postalCode: string;
    city: string;
    country: string;
    user_id: string;
}

export interface IUser {
    id: string;
    username: string;
    role: string;
}