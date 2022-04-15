package com.khoders.asset.dto;

import com.khoders.asset.entities.Location;

public class AssetTransferDto extends BaseDto
{
    private Location transferFrom;
    private Location transferTo;
    private Location transferDate;
    private Location description;

    public Location getTransferFrom()
    {
        return transferFrom;
    }

    public void setTransferFrom(Location transferFrom)
    {
        this.transferFrom = transferFrom;
    }

    public Location getTransferTo()
    {
        return transferTo;
    }

    public void setTransferTo(Location transferTo)
    {
        this.transferTo = transferTo;
    }

    public Location getTransferDate()
    {
        return transferDate;
    }

    public void setTransferDate(Location transferDate)
    {
        this.transferDate = transferDate;
    }

    public Location getDescription()
    {
        return description;
    }

    public void setDescription(Location description)
    {
        this.description = description;
    }
}
